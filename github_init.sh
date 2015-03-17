Script started on Tuesday 17 March 2015 02:12:42 AM IST
]0;raj@raj-SVF15212SNB: ~/evolution_research/FISPrototype/FISPrototype/srcraj@/home/raj/evolution_research/FISPrototype/FISPrototype/src git all .[K[K[K[Kdd .
]0;raj@raj-SVF15212SNB: ~/evolution_research/FISPrototype/FISPrototype/srcraj@/home/raj/evolution_research/FISPrototype/FISPrototype/src man git[K[K[K[K[K[K[Kgit commit -m 'initial comm[K[K[K[K[K[Kl commit for FIS'

*** Please tell me who you are.

Run

  git config --global user.email "you@example.com"
  git config --global user.name "Your Name"

to set your account's default identity.
Omit --global to set the identity only in this repository.

fatal: unable to auto-detect email address (got 'raj@raj-SVF15212SNB.(none)')
]0;raj@raj-SVF15212SNB: ~/evolution_research/FISPrototype/FISPrototype/srcraj@/home/raj/evolution_research/FISPrototype/FISPrototype/src git config user.email "rajcheruk@gmail.com"
]0;raj@raj-SVF15212SNB: ~/evolution_research/FISPrototype/FISPrototype/srcraj@/home/raj/evolution_research/FISPrototype/FISPrototype/src git config user.name "Raj Cherukuri"
]0;raj@raj-SVF15212SNB: ~/evolution_research/FISPrototype/FISPrototype/srcraj@/home/raj/evolution_research/FISPrototype/FISPrototype/src git config user.name "Raj Cherukuri"email "rajcheruk@gmail.com"[5Pmmit -m 'initial commit for FIS'
[master (root-commit) 94657c9] initial commit for FIS
 27 files changed, 4799 insertions(+)
 create mode 100755 fisprototype/BinaryRegion.java
 create mode 100755 fisprototype/CannyEdgeDetector.java
 create mode 100755 fisprototype/ColorEdgeDetector.java
 create mode 100755 fisprototype/Contour.java
 create mode 100755 fisprototype/ContourOverlay.java
 create mode 100755 fisprototype/Demo_RegionLabeling.java
 create mode 100755 fisprototype/Demo_RegionsAndContours.java
 create mode 100755 fisprototype/FISPrototypeUI.java
 create mode 100755 fisprototype/GaussianOctave.java
 create mode 100755 fisprototype/GaussianScaleSpace.java
 create mode 100755 fisprototype/HierarchicalScaleSpace.java
 create mode 100755 fisprototype/Image_Pyramid.java
 create mode 100755 fisprototype/LoG.java
 create mode 100755 fisprototype/Make_Projections.java
 create mode 100755 fisprototype/RegionContourStack.java
 create mode 100755 fisprototype/ScaleLevel.java
 create mode 100755 fisprototype/ScaleOctave.java
 create mode 100755 fisprototype/Trace_Contours.java
 create mode 100755 fisprototype/Watershed_Algorithm.jar
 create mode 100755 fisprototype/condimentNoise.java
 create mode 100755 fisprototype/gaussianFilter.java
 create mode 100755 fisprototype/gaussianNoise.java
 create mode 100644 fisprototype/laplacian_Gaussian.java
 create mode 100644 fisprototype/laplacian_GaussianImpl.java
 create mode 100644 github_init.sh
 create mode 100755 ij/IP_Demo.java
 create mode 100755 ij/ImageJ.java
]0;raj@raj-SVF15212SNB: ~/evolution_research/FISPrototype/FISPrototype/src/fisprototyperaj@/home/raj/evolution_research/FISPrototype/FISPrototype/src/fisprototype cd ..
]0;raj@raj-SVF15212SNB: ~/evolution_research/FISPrototype/FISPrototype/srcraj@/home/raj/evolution_research/FISPrototype/FISPrototype/src git remote add origin https://github.com/rajcheruk/FIS.git
]0;raj@raj-SVF15212SNB: ~/evolution_research/FISPrototype/FISPrototype/srcraj@/home/raj/evolution_research/FISPrototype/FISPrototype/src git [K[K[K[Kgit pull origin master
warning: no common commits
remote: Counting objects: 3, done.[K
remote: Compressing objects:  50% (1/2)   [Kremote: Compressing objects: 100% (2/2)   [Kremote: Compressing objects: 100% (2/2), done.[K
remote: Total 3 (delta 0), reused 0 (delta 0), pack-reused 0[K
Unpacking objects:  33% (1/3)   Unpacking objects:  66% (2/3)   Unpacking objects: 100% (3/3)   Unpacking objects: 100% (3/3), done.
From https://github.com/rajcheruk/FIS
 * branch            master     -> FETCH_HEAD
 * [new branch]      master     -> origin/master
[?1049h[1;53r(B[m[4l[?7h[?12l[?25h[?1h=[?1h=[?1h=[39;49m[39;49m(B[m[H[2J(B[0;7m  GNU nano 2.2.6           File: /home/raj/evolution_research/FISPrototype/FISPrototype/src/.git/MERGE_MSG                             [3;1H(B[mMerge branch 'master' of https://github.com/rajcheruk/FIS[5d# Please enter a commit message to explain why this merge is necessary,[6d# especially if it merges an updated upstream into a topic branch.[7d#[8d# Lines starting with '#' will be ignored, and an empty message aborts[9d# the commit.[51;60H(B[0;7m[ Read 7 lines ][52d^G(B[m Get Help[52;23H(B[0;7m^O(B[m WriteOut[52;45H(B[0;7m^R(B[m Read File[52;67H(B[0;7m^Y(B[m Prev Page[52;89H(B[0;7m^K(B[m Cut Text[52;111H(B[0;7m^C(B[m Cur Pos[53d(B[0;7m^X(B[m Exit[53;23H(B[0;7m^J(B[m Justify[53;45H(B[0;7m^W(B[m Where Is[53;67H(B[0;7m^V(B[m Next Page[53;89H(B[0;7m^U(B[m UnCut Text[53;111H(B[0;7m^T(B[m To Spell[3d[10d[1;126H(B[0;7mModified[10d(B[mBuilding Initial R Source[51d[K[10;24H o Code Repository - 17/03/2015[51d(B[0;7mFile Name to Write: /home/raj/evolution_research/FISPrototype/FISPrototype/src/.git/MERGE_MSG                                          [52;23H(B[m           (B[0;7mM-D(B[m DOS Format         [52;67H(B[0;7mM-A(B[m Append  [52;89H           (B[0;7mM-B(B[m Backup File[K[53;2H(B[0;7mC(B[m Cancel[53;23H           (B[0;7mM-M(B[m Mac Format        [53;67H(B[0;7mM-P(B[m Prepend[K[51;94H[1;126H[39;49m(B[0;7m        [51;59H(B[m[1K (B[0;7m[ Wrote 8 lines ](B[m[K[52;23H(B[0;7m^O(B[m WriteOut           (B[0;7m^R(B[m Read File[52;67H(B[0;7m^Y(B[m Prev Page[52;89H(B[0;7m^K(B[m Cut Text           (B[0;7m^C(B[m Cur Pos[53;2H(B[0;7mX(B[m Exit  [53;23H(B[0;7m^J(B[m Justify            (B[0;7m^W(B[m Where Is[53;67H(B[0;7m^V(B[m Next Page[53;89H(B[0;7m^U(B[m UnCut Text[53;111H(B[0;7m^T(B[m To Spell[10;53H[52d[J[53;135H[53;1H[?1049l[?1l>Merge made by the 'recursive' strategy.
 README.md | 2 [32m++[m
 1 file changed, 2 insertions(+)
 create mode 100644 README.md
]0;raj@raj-SVF15212SNB: ~/evolution_research/FISPrototype/FISPrototype/srcraj@/home/raj/evolution_research/FISPrototype/FISPrototype/src git push origin master
Username for 'https://github.com': rajcheruk
Password for 'https://rajcheruk@github.com': 
Counting objects: 33, done.
Delta compression using up to 4 threads.
Compressing objects:   3% (1/32)   Compressing objects:   6% (2/32)   Compressing objects:   9% (3/32)   Compressing objects:  12% (4/32)   Compressing objects:  15% (5/32)   Compressing objects:  18% (6/32)   Compressing objects:  21% (7/32)   Compressing objects:  25% (8/32)   Compressing objects:  28% (9/32)   Compressing objects:  31% (10/32)   Compressing objects:  34% (11/32)   Compressing objects:  37% (12/32)   Compressing objects:  40% (13/32)   Compressing objects:  43% (14/32)   Compressing objects:  46% (15/32)   Compressing objects:  50% (16/32)   Compressing objects:  53% (17/32)   Compressing objects:  56% (18/32)   Compressing objects:  59% (19/32)   Compressing objects:  62% (20/32)   Compressing objects:  65% (21/32)   Compressing objects:  68% (22/32)   Compressing objects:  71% (23/32)   Compressing objects:  75% (24/32)   Compressing objects:  78% (25/32)   Compressing objects:  81% (26/32)   Compressing objects:  84% (27/32)   Compressing objects:  87% (28/32)   Compressing objects:  90% (29/32)   Compressing objects:  93% (30/32)   Compressing objects:  96% (31/32)   Compressing objects: 100% (32/32)   Compressing objects: 100% (32/32), done.
Writing objects:   3% (1/33)   Writing objects:   6% (2/33)   Writing objects:   9% (3/33)   Writing objects:  12% (4/33)   Writing objects:  15% (5/33)   Writing objects:  18% (6/33)   Writing objects:  21% (7/33)   Writing objects:  24% (8/33)   Writing objects:  27% (9/33)   Writing objects:  30% (10/33)   Writing objects:  33% (11/33)   Writing objects:  36% (12/33)   Writing objects:  39% (13/33)   Writing objects:  42% (14/33)   Writing objects:  45% (15/33)   Writing objects:  48% (16/33)   Writing objects:  51% (17/33)   Writing objects:  54% (18/33)   Writing objects:  57% (19/33)   Writing objects:  60% (20/33)   Writing objects:  63% (21/33)   Writing objects:  66% (22/33)   Writing objects:  69% (23/33)   Writing objects:  72% (24/33)   Writing objects:  75% (25/33)   Writing objects:  78% (26/33)   Writing objects:  81% (27/33)   Writing objects:  84% (28/33)   Writing objects:  87% (29/33)   Writing objects:  90% (30/33)   Writing objects:  93% (31/33)   Writing objects:  96% (32/33)   Writing objects: 100% (33/33)   Writing objects: 100% (33/33), 66.86 KiB | 0 bytes/s, done.
Total 33 (delta 3), reused 0 (delta 0)
To https://github.com/rajcheruk/FIS.git
   2c364b9..175bc54  master -> master

Script done on Tuesday 17 March 2015 02:35:34 AM IST
