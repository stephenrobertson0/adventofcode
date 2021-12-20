package _2021;


public class _17 {
    
    public static void a() throws Exception {
        int xTargetAreaMin = 94;
        int xTargetAreaMax = 151;
        int yTargetAreaMin = -156;
        int yTargetAreaMax = -103;
        
        int overallMaxYPosition = 0;
        
        for (int x = 0; x < 200; x++) {
            for (int y = 0; y < 500; y++) {
                int xPos = 0;
                int yPos = 0;
                
                int xVelocity = x;
                int yVelocity = y;
                
                int maxYPosition = 0;
                
                for (int j = 0; j < 500; j++) {
                    
                    xPos += xVelocity;
                    yPos += yVelocity;
                    
                    if (yPos > maxYPosition) {
                        maxYPosition = yPos;
                    }
                    
                    //System.out.println("xy Pos: " + xPos + "," + yPos);
                    
                    if (xPos >= xTargetAreaMin && xPos <= xTargetAreaMax && yPos >= yTargetAreaMin
                            && yPos <= yTargetAreaMax) {
                        if (maxYPosition > overallMaxYPosition) {
                            overallMaxYPosition = maxYPosition;
                        }
                    }
                    
                    if (xVelocity > 0) {
                        xVelocity--;
                    } else if (xVelocity < 0) {
                        xVelocity++;
                    }
                    
                    yVelocity--;
                    
                }
                
            }
            
        }
        
        System.out.println(overallMaxYPosition);
    }
    
    public static void b() throws Exception {
        int xTargetAreaMin = 94;
        int xTargetAreaMax = 151;
        int yTargetAreaMin = -156;
        int yTargetAreaMax = -103;
        
        int overallCount = 0;
        
        for (int x = 0; x < 200; x++) {
            for (int y = -500; y < 500; y++) {
                int xPos = 0;
                int yPos = 0;
                
                int xVelocity = x;
                int yVelocity = y;
                
                boolean success = false;
                
                for (int j = 0; j < 500; j++) {
                    
                    xPos += xVelocity;
                    yPos += yVelocity;
                    
                    //System.out.println("xy Pos: " + xPos + "," + yPos);
                    
                    if (xPos >= xTargetAreaMin && xPos <= xTargetAreaMax && yPos >= yTargetAreaMin
                            && yPos <= yTargetAreaMax) {
                        success = true;
                    }
                    
                    if (xVelocity > 0) {
                        xVelocity--;
                    } else if (xVelocity < 0) {
                        xVelocity++;
                    }
                    
                    yVelocity--;
                    
                }
                
                if (success) {
                    overallCount++;
                }
                
            }
            
        }
        
        System.out.println(overallCount);
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}