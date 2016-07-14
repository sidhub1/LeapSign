
import com.leapmotion.leap.Finger;

/***
 * 
 * @author Team Explore
 * The Letter Chooser class contains the letter chooser method and send the letter
 * that the user has signaled to the LeapSignListener class, where it is displayed.
 *
 */
public class LetterChooser {
   //Counter Variable
   private String sentence;
   private Finger thumb;
   private Finger index;
   private Finger middle;
   private Finger ring;
   private Finger pinky;
   public LetterChooser(Finger thumb, Finger index, Finger middle, Finger ring, Finger pinky){
      this.thumb = thumb;
      this.index = index;
      this.middle = middle;
      this.ring = ring;
      this.pinky = pinky;
   }
   /****
    * The getLetter class returns the letter based on the hand movements. It uses methods from LeapMotion,
    * such as isExtended() and direction() to distinguish between letters. The method can be modified to
    * include more letters and numbers
    * @return letter
    */
   public String selectOption(){
	   if(!pinky.isExtended() && !ring.isExtended() && !middle.isExtended() && index.isExtended() && !thumb.isExtended())
		   return "PARTS";
	   else if(!pinky.isExtended() && !ring.isExtended() && middle.isExtended() && index.isExtended() && !thumb.isExtended())
		   return "TOOLS";
	   return "NULL";
   }
   public int getTool(){
	   if(!pinky.isExtended() && !ring.isExtended() && !middle.isExtended() && index.isExtended() && !thumb.isExtended())
		   return 1;
	   if(!pinky.isExtended() && !ring.isExtended() && middle.isExtended() && index.isExtended() && !thumb.isExtended())
		   return 2;
	   if(!pinky.isExtended() && ring.isExtended() && middle.isExtended() && index.isExtended() && !thumb.isExtended())
		   return 3;
	   if(pinky.isExtended() && ring.isExtended() && middle.isExtended() && index.isExtended() && !thumb.isExtended())
		   return 4;
	   if(pinky.isExtended() && ring.isExtended() && middle.isExtended() && index.isExtended() && thumb.isExtended())
		   return 5;
	   if(!pinky.isExtended() && !ring.isExtended() && !middle.isExtended() && !index.isExtended() && !thumb.isExtended())
		   return 0;
	   return -1;
   }
   
   public String getLetter(){ // Left: C, D, F, G, I, J, 
      if (!pinky.isExtended() && thumb.isExtended() && middle.isExtended()
            && ring.isExtended() && index.isExtended()){
         return "I";
      }
      if (pinky.isExtended() && !ring.isExtended() && middle.isExtended() && index.isExtended() && !thumb.isExtended()){
         return "D";
      }
      if ((thumb.direction().getX() > 0.7 && thumb.direction().getX() < 0.9) && !index.isExtended() && !middle.isExtended() && !ring.isExtended() && !pinky.isExtended()){
         return "N";
      }
      
      String letter = "";
      if ((thumb.isExtended()) && !(index.isExtended()) && 
            !(middle.isExtended()) && !(ring.isExtended()) && !(pinky.isExtended()) && (thumb.direction().getX() < -0.7 && thumb.direction().getX() > -0.9 )){
         return "A";
      }
      if (!(thumb.isExtended()) && !(index.isExtended()) && 
            !(middle.isExtended()) && !(ring.isExtended()) && !(pinky.isExtended())){
         return "E";
      }
      if (index.isExtended() && !thumb.isExtended() && 
            !middle.isExtended() && !ring.isExtended() && !pinky.isExtended()){
         return "R";
      }
      
      if (thumb.isExtended()){ //K, L, O, P, Q, Y
         float pkAngle = index.direction().angleTo(middle.direction());
         if (index.isExtended() && middle.isExtended() && !ring.isExtended() && !pinky.isExtended() && (pkAngle < 70 && pkAngle > 0)){
            return "K";
         }
         if (index.isExtended() && !middle.isExtended() && !ring.isExtended() 
               && !pinky.isExtended() && (thumb.direction().getX() < -0.8 && thumb.direction().getX() > -0.99)){
            return "T";
         }
         
         if (index.isExtended() && middle.isExtended() && (pkAngle >70 && pkAngle < 110)){
            return "P";
         }
         //Q
         float indexZ = index.direction().getZ();
         float thumbZ = thumb.direction().getZ();
         if ( (indexZ > 0.200 && indexZ < 0.400) && (thumbZ > 0.200 && thumbZ < 0.400) 
               && !middle.isExtended() && !ring.isExtended() && !pinky.isExtended()){
            return "Q";
         }
         
      }
      if (thumb.isExtended() && index.isExtended() && middle.isExtended() && ring.isExtended() && pinky.isExtended()){
         return " ";
      }
      if (index.isExtended()){ //if index is extended
         if (middle.isExtended()){ //if the middle finger is extended
            //B, H, K, R, U, V, W
            if (ring.isExtended() && pinky.isExtended() && !(thumb.isExtended()) && (index.direction().getX() > 0 && index.direction().getX() < 0.45)){
               return "B";
            }
            float indexX = index.direction().getX();
            if (!ring.isExtended() && !pinky.isExtended() && (indexX > -0.800 && indexX < 0.100)){
               return "H";
            }
            float uvwAngle = middle.direction().angleTo(index.direction());
            if (!thumb.isExtended() && !ring.isExtended() && !pinky.isExtended() && (uvwAngle > -Math.PI/6 && uvwAngle < Math.PI/6)){
               return "U";
            }
            if (!thumb.isExtended() && !ring.isExtended() && !pinky.isExtended() && (uvwAngle > Math.PI/6 && uvwAngle < Math.PI/3)){
               return "V";
            }
            if (!thumb.isExtended() && ring.isExtended() && !pinky.isExtended() && middle.isExtended() && index.isExtended()){
               return "W";
            }
         }
         
      }
      //O: sideways 3-point
      float indexX2 = index.direction().getX();
      if (middle.isExtended() && ring.isExtended() && pinky.isExtended() && (indexX2 > -0.700 && indexX2 < -0.999) && !thumb.isExtended()){
         return "O";
      }
      if (pinky.isExtended() && ring.isExtended() && !middle.isExtended() && index.isExtended() && !thumb.isExtended()){
         return "L";
      }
      
      //C
      float middleX = middle.direction().getX();
      if (middle.isExtended() && ring.isExtended() && pinky.isExtended() && !index.isExtended() && (middleX > -0.800 && middleX < 0.100)){
         return "C";
      }

        float thumbX = thumb.direction().getX();
        if (!middle.isExtended() && !ring.isExtended() && ! pinky.isExtended() && (thumbX > 0.8 && thumbX < 0.99)){
            return "."; 
        }
      
      return letter;
   }
}