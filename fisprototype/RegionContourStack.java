/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author RAJABHUSHANAM
 */

package fisprototype;

import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.WindowManager;
import ij.gui.*;
import ij.plugin.filter.PlugInFilter;
import ij.process.*;

public class RegionContourStack implements PlugInFilter {

    static int nFrames = 9;
    ImagePlus  fgIMG;
    
    @Override
    public int setup(String arg, ImagePlus imp)     {
        return DOES_ALL;
    }
    
    boolean runDialog()   {
        int[] windowList = WindowManager.getIDList();
        if (windowList == null)   {
            IJ.noImage();
            return false;
    }
        
    String[] WindowFiles = new String[windowList.length];
    for (int i=0; i < windowList.length; i++ )    {
        ImagePlus imp = WindowManager.getImage(windowList[i]);
        if (imp  != null)   {
            WindowFiles[i] = imp.getShortTitle();
        }
        else
            WindowFiles[i] = "Missing Images....";
    }
    
    GenericDialog gd = new GenericDialog("Alpha Blending...");
    gd.addChoice("Foreground Image:", WindowFiles, WindowFiles[0]);
    gd.addNumericField("Frames:", nFrames, 0);
    gd.showDialog();
    if (gd.wasCanceled())
        return false;
    else  {
        int img2Index = gd.getNextChoiceIndex();
        fgIMG = WindowManager.getImage(WindowFiles[img2Index]);
        nFrames = (int) gd.getNextNumber();
        if (nFrames < 2)   
            nFrames = 2;
        return true;
    }
  }

    @Override
    public void run(ImageProcessor bgIP)    {
    
    if (runDialog())    {
        
        int w = bgIP.getWidth();
        int h = bgIP.getHeight();
        
        ImageProcessor fgIP = fgIMG.getProcessor().convertToByte(false);
        ImageProcessor fgTmpIP = bgIP.duplicate();
        
        ImagePlus stack = NewImage.createByteImage("STACK", w, h, nFrames, 0);
        ImageStack Img_stack = stack.getStack();
        
        for(int i= 0; i < nFrames; i++)
        {
            double iAlpha = 1.0 - (double)i / (nFrames -1);
            ImageProcessor iFrame = Img_stack.getProcessor(i+1);
            
            iFrame.insert(bgIP, 0, 0);
            iFrame.multiply(1-iAlpha);
            
            fgTmpIP.insert(fgIP, 0, 0);
            fgTmpIP.multiply(1-iAlpha);
            
            ByteBlitter blitter = new ByteBlitter((ByteProcessor) iFrame);
            blitter.copyBits(fgTmpIP, 0, 0, Blitter.ADD);
        }  
            
        stack.show();
    }
   }
}
