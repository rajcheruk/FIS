//=====================================================
//      Name:           GLCM_Texture
//      Project:         Gray Level Correlation Matrix Texture Analyzer
//      Version:         0.4
//
//      Author:           Julio E. Cabrera
//      Date:             06/10/05
//      Comment:       Calculates texture features based in Gray Level Correlation Matrices
//			   Changes since 0.1 version: The normalization constant (R in Haralick's paper, pixelcounter here)
//			   now takes in account the fact that for each pair of pixel you take in account there are two entries to the 
//			   grey level co-ocurrence matrix
//	 		   Changes were made also in the Correlation parameter. Now this parameter is calculated according to Walker's paper
//=====================================================

package ij;

//===========imports===================================
import ij.*;
import ij.gui.*;
import ij.plugin.filter.PlugInFilter;
import ij.process.*;
import ij.process.FloatProcessor;
import java.awt.*;
import ij.plugin.PlugIn;
import ij.text.*;
import ij.measure.ResultsTable;
import java.lang.Number;

//===========source====================================
public class GLCM_Texture implements PlugInFilter {

    static int step = 5;
    static String selectedStep = "0 degrees";
    static boolean doIcalculateASM = true;
    static boolean doIcalculateContrast = true;
    static boolean doIcalculateCorrelation = true;
    static boolean doIcalculateIDM = true;
    static boolean doIcalculateEntropy = true;
    static boolean doIcalculateLRE = true;
    static boolean doIcalculateGLD = true;
    static boolean doIcalculateRLD = true;
    static boolean doIcalculateRP = true;    
        
    public int     floatWidth=0;
    public int     floatHeight=0;
    public FloatProcessor TextureFP = null;
    public ImageConverter   iconv=null;
    public ImagePlus imp1 = null;
    
    ResultsTable rt = ResultsTable.getResultsTable();

    public int setup(String arg, ImagePlus imp) {
        if (imp != null && !showDialog()) {
            iconv = new ImageConverter(imp);
            iconv.convertToGray8();
            return DONE;
        }
        rt.reset();
        return DOES_8G + DOES_STACKS + SUPPORTS_MASKING;
    }

    public void run(ImageProcessor ip) {

        Rectangle r = ip.getRoi();
        
        if (!(ip instanceof ByteProcessor)) return;
            if(!(ip.getPixels() instanceof byte[])) return;
            
        // This part get al the pixel values into the pixel [ ] array via the Image Processor
        byte[] pixels = (byte[]) ip.getPixels();
    
        int width = ip.getWidth(); 
        
        floatWidth = r.x + r.width;
        floatHeight = r.y + r.height;
        
        TextureFP = new FloatProcessor(floatWidth, floatHeight);
        TextureFP = (FloatProcessor) ip.convertToFloat();
        
        // The variable a holds the value of the pixel where the Image Processor is sitting its attention
        // The varialbe b holds the value of the pixel which is the neighbor to the  pixel where the Image Processor is sitting its attention
        int a;
        int b;
        float pixelCounter = 0;

        //====================================================================================================
        // This part computes the Gray Level Correlation Matrix based in the step selected by the user
        //Based on BRDF -- Rajabhushanam
        
        int offset, i;
        float[][] glcm= new float[floatWidth][floatHeight];

        if (selectedStep.equals("0 degrees")) {

            for (int y = r.y; y < (r.y + r.height); y++) {
                offset = y * width;
                for (int x = r.x; x < (r.x + r.width); x++) {
                    i = offset + x;

                    a = (0xFF & pixels[i]);
                    b = (0xFF & (ip.getPixel(x + step, y)));
                    //b = (int) Float.intBitsToFloat(ip.getPixel(x + step, y));
                    //b = 0xff & b;
                    
                    glcm[a][b] += 1;
                    glcm[b][a] += 1;
                    pixelCounter += 2;
                    
                }
            }
        }

                    //float [] [] glcmf= new float [257][257];
                    //for (a=0;  a<257; a++)  {
                    //	for (b=0; b<257;b++) {
                    //		glcmf[a][b]=(float)glcm[a][b];
                    //	}
                    //}
                    //new ImagePlus("glcm", new FloatProcessor(glcmf)).show();	

        if (selectedStep.equals("90 degrees")) {

            for (int y = r.y; y < (r.y + r.height); y++) {
                offset = y * width;
                for (int x = r.x; x < (r.x + r.width); x++) {
                    i = offset + x;

                    a = (0xFF & pixels[i]);
                    b = (0xFF & (ip.getPixel(x, y - step)));
                    glcm[a][b] += 1;
                    glcm[b][a] += 1;
                    pixelCounter += 2;

                }
            }
        }

        if (selectedStep.equals("180 degrees")) {

            for (int y = r.y; y < (r.y + r.height); y++) {
                offset = y * width;
                for (int x = r.x; x < (r.x + r.width); x++) {
                    i = offset + x;

                    a = (0xFF & pixels[i]);
                    b = (0xFF & (ip.getPixel(x - step, y)));
                    glcm[a][b] += 1;
                    glcm[b][a] += 1;
                    pixelCounter += 2;
                    
                }
            }
        }

        if (selectedStep.equals("270 degrees")) {

            for (int y = r.y; y < (r.y + r.height); y++) {
                offset = y * width;
                for (int x = r.x; x < (r.x + r.width); x++) {
                    i = offset + x;

                    a = (0xFF & pixels[i]);
                    b = (0xFF & (ip.getPixel(x, y + step)));
                    glcm[a][b] += 1;
                    glcm[b][a] += 1;
                    pixelCounter += 2;

                }
            }
        }
//=====================================================================================================

// This part divides each member of the glcm matrix by the number of pixels. The number of pixels was stored in the pixelCounter variable
// The number of pixels is used as a normalizing constant
        for (a = 0; a < floatWidth; a++) {
            for (b = 0; b < floatHeight; b++) {
                //glcm[a][b]= Float.intBitsToFloat((int) (glcm[a][b] / pixelCounter));
                glcm[a][b] = (float) ((glcm[a][b]) / (pixelCounter));
                //System.out.printf("%d %d %f %n", a, b, glcm[a][b]);
            }
        }

        rt.incrementCounter();
        int row = rt.getCounter() - 1;
//=====================================================================================================
// This part calculates the angular second moment; the value is stored in asm

        if (doIcalculateASM == true) {
            double asm = 0.0;
            for (a = 0; a < floatWidth; a++) {
                for (b = 0; b < floatHeight; b++) {
                    asm = asm + (glcm[a][b] * glcm[a][b]);
                }
            }
            rt.setValue("Angular Second Moment", row, asm);
        }

//=====================================================================================================
// This part calculates the contrast; the value is stored in contrast
        if (doIcalculateContrast == true) {
            double contrast = 0.0;
            for (a = 0; a < floatWidth; a++) {
                for (b = 0; b < floatHeight; b++) {
                    contrast = contrast + (a - b) * (a - b) * (glcm[a][b]);
                }
            }
            rt.setValue("Contrast", row, contrast);
        }

//=====================================================================================================
//This part calculates the correlation; the value is stored in correlation
// px []  and py [] are arrays to calculate the correlation
// meanx and meany are variables  to calculate the correlation
//  stdevx and stdevy are variables to calculate the correlation
        if (doIcalculateCorrelation == true) {

//First step in the calculations will be to calculate px [] and py []
            double correlation = 0.0;
            double px = 0;
            double py = 0;
            double meanx = 0.0;
            double meany = 0.0;
            double stdevx = 0.0;
            double stdevy = 0.0;

            for (a = 0; a < floatWidth; a++) {
                for (b = 0; b < floatHeight; b++) {
                    px = px + a * glcm[a][b];
                    py = py + b * glcm[a][b];

                }
            }

// Now calculate the standard deviations
            for (a = 0; a < floatWidth; a++) {
                for (b = 0; b < floatHeight; b++) {
                    stdevx = stdevx + (a - px) * (a - px) * glcm[a][b];
                    stdevy = stdevy + (b - py) * (b - py) * glcm[a][b];
                }
            }

// Now finally calculate the correlation parameter
            for (a = 0; a < floatWidth; a++) {
                for (b = 0; b < floatHeight; b++) {
                    correlation = correlation + ((a - px) * (b - py) * glcm[a][b] / (stdevx * stdevy));
                }
            }
            rt.setValue("Correlation", row, correlation);

        }
//===============================================================================================
// This part calculates the inverse difference moment

        if (doIcalculateIDM == true) {
            double IDM = 0.0;
            for (a = 0; a < floatWidth; a++) {
                for (b = 0; b < floatHeight; b++) {
                    IDM = IDM + (glcm[a][b] / (1 + (a - b) * (a - b)));
                }
            }
            rt.setValue("Inverse Difference Moment   ", row, IDM);

        }

//===============================================================================================
// This part calculates the entropy
        if (doIcalculateEntropy == true) {
            double entropy = 0.0;
            for (a = 0; a < floatWidth; a++) {
                for (b = 0; b < floatHeight; b++) {
                    if (glcm[a][b] == 0) {
                    } else {
                        entropy = entropy - (glcm[a][b] * (Math.log(glcm[a][b])));
                    }
                }
            }
            rt.setValue("Entropy", row, entropy);

        }

        double suma = 0.0;
        for (a = 0; a < floatWidth; a++) {
            for (b = 0; b < floatHeight; b++) {
                suma = suma + glcm[a][b];
            }
        }
        rt.setValue("Sum of all GLCM elements", row, suma);
        
        
        //=====================================================================================================
        // This part calculates the Long Run Emphasis - LRE

        if (doIcalculateLRE == true) {
            double lre = 0.0;
            for (a = 0; a < floatWidth; a++) {
                for (b = 0; b < floatHeight; b++) {
                    lre = lre + ((b * b * glcm[a][b]) / glcm[a][b]);
                }
            }
            System.out.printf("Long Run Emphasis: %f", lre);
            rt.setValue("Long Run Emphasis", row, lre);
        }
        
        // Gray Level Distribution - GLD
        if (doIcalculateGLD == true) {
            double gld = 0.0;
            for (a = 0; a < floatWidth; a++) {
                for (b = 0; b < floatHeight; b++) {
                    gld = gld + (a * (b * glcm[a][b] * glcm[a][b])) / glcm[a][b];
                }
            }
            System.out.printf("Gray Level Distribution: %f", gld);
            rt.setValue("Gray Level Distribution", row, gld);
        }
        
        // Run Length Distribution - RLD
        if (doIcalculateRLD == true) {
            double rld = 0.0;
            for (a = 0; a < floatWidth; a++) {
                for (b = 0; b < floatHeight; b++) {
                    rld = rld + (b * (a * glcm[a][b] * glcm[a][b])) / glcm[a][b];  
                }
            }
            System.out.printf("Run Length Distribution: %f", rld);
                    
            rt.setValue("Run Length Distribution", row, rld);
        }

        // Run Percentage - RP
        if (doIcalculateRP == true) {
            double rp = 0.0;
            for (a = 0; a < floatWidth; a++) {
                for (b = 0; b < floatHeight; b++) {
                    rp = rp + (glcm[a][b]/(a*b)*(a*b));
                }
            }
            System.out.printf("Run Percentage: %f", rp);
            rt.setValue("Run Percentage", row, rp);
        }

        rt.show("Results");

//===============================================================================================
        //TextWindow tw = new TextWindow("Haralick's texture features   ", "", 400, 200);
	//tw.append("  ");
	//tw.append ("Total pixels analyzed  "+ pixelCounter);
	//tw.append ( "Selected Step   " + selectedStep);
	//tw.append ("Size of the step   "+ step);
	//tw.append ("3 a la quinta   "+ Math.pow(3,5));
       
            TextureFP = new FloatProcessor(glcm);
            imp1 = new ImagePlus("Gray Level Cooccurance Matrix - Texture Measure", TextureFP);
            iconv = new ImageConverter(imp1);
            iconv.convertToGray8();
            imp1.show();        
    }

    
    // This part is the implementation of the Dialog box (called gd here)
    boolean showDialog() {
        GenericDialog gd = new GenericDialog("Textural features based in GLCM. Version 0.4");
        gd.addMessage("This plug-in calculates textural features\n" + " based in Gray Level Correlation Matrices.");

        gd.addNumericField("Enter the size of the step in pixels", step, 0);

        String[] stepOptions = {"0 degrees", "90 degrees", "180 degrees", "270 degrees"};
        gd.addChoice("Select the direction of the step", stepOptions, selectedStep);

        gd.addMessage("Check in the following boxes\n" + "for the parameters you want to compute \n" + "and click OK.");
        gd.addCheckbox("Angular Second Moment  ", doIcalculateASM);
        gd.addCheckbox("Contrast  ", doIcalculateContrast);
        gd.addCheckbox("Correlation  ", doIcalculateCorrelation);
        gd.addCheckbox("Inverse Difference Moment  ", doIcalculateIDM);
        gd.addCheckbox("Entropy   ", doIcalculateEntropy);
        gd.addCheckbox("Long Run Emphasis ", doIcalculateLRE);
        gd.addCheckbox("Gray Level Distribution ", doIcalculateGLD);
        gd.addCheckbox("Run Length Distribution ", doIcalculateRLD);
        gd.addCheckbox("Run Percentage ", doIcalculateRP);
                
                
        gd.showDialog();
        if (gd.wasCanceled()) {
            return false;
        }

        step = (int) gd.getNextNumber();
        selectedStep = gd.getNextChoice();
        doIcalculateASM = gd.getNextBoolean();
        doIcalculateContrast = gd.getNextBoolean();
        doIcalculateCorrelation = gd.getNextBoolean();
        doIcalculateIDM = gd.getNextBoolean();
        doIcalculateEntropy = gd.getNextBoolean();
        doIcalculateLRE = gd.getNextBoolean();
        doIcalculateGLD = gd.getNextBoolean();
        doIcalculateRLD = gd.getNextBoolean();
        doIcalculateRP = gd.getNextBoolean();
        
        return true;
    }
}
