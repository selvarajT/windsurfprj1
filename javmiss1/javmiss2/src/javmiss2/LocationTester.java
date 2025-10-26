package javmiss2;


public class LocationTester
{
 public static void main(String[] args)
 {
 SimpleLocation ucsd = new SimpleLocation(13.029133857984698, 80.20887698149879);
 SimpleLocation lima = new SimpleLocation(9.948960, 78.189840);
 System.out.println(ucsd.distance(lima));
 }
}
 