package adda.util;

public class Titles {
	public static String F1 = 
			"""
####### ##### #       #######       #   
#         #   #       #            ##   
#         #   #       #           # #   
#####     #   #       #####         #   
#         #   #       #             #   
#         #   #       #             #   
#       ##### ####### #######     ##### 
			
			""";
	
	public static String F2 = 
			"""
\n\n\n
####### ##### #       #######     #####  
#         #   #       #          #     # 
#         #   #       #                # 
#####     #   #       #####       #####  
#         #   #       #          #       
#         #   #       #          #       
#       ##### ####### #######    #######
			
			""";
	
	public static String F3 = 
			"""
\n\n\n
####### ##### #       #######     #####  
#         #   #       #          #     # 
#         #   #       #                # 
#####     #   #       #####       #####  
#         #   #       #                # 
#         #   #       #          #     # 
#       ##### ####### #######     ##### 
			
			""";
	public static String getTitle(int i) {
		return switch(i) {
		case 1: yield F1;
		case 2: yield F2;
		case 3: yield F3;
		default: yield "INVALID";
		};
	}
}
