import java.util.Random;

public class Pso {
	// #Fitness fonksiyonumu tanýmlýyorum.
	
		public static double fitness(double[] x){
	        double sum = 0;

	        for(int i=0; i<x.length; i++){
	            sum= sum + Math.pow(x[i], 2);            
	        }

	        return sum;

	    }
		
	    public static void main(String[] args){
			
	    	//Deðiþken Tanýmlama
	    	
	    	int		swarm_size 	= 10,  // # swarm_size 
					D 		  	= 5,   // # Dimensions
					iter 		= 100; // # Ýterasyon sayýsý 
			
	        double w         = 0.9,   wmax_coef = 0.1,
	               c1        = 2,  	  c2        = 2, 
	               r1        = 0.0,   r2        = 0.0,
				   LB        = -5.12, UB        = 5.12,   
	               vMin      = 0,     vMax      = 1,     
	               nInfinite = Double.NEGATIVE_INFINITY,
	               gBestValue = nInfinite;
	        
	    
	        //Tek boyutlu dizi oluþturma ve doldurma.
	        
	        double[] pBestValue         = new double[swarm_size ],
	                 gBest		        = new double[D],    		
	                 fitnes_fun			= new double[iter], 		
	                 M                  = new double[swarm_size ];
	        //Ýki boyutlu dizi oluþturma ve doldurma.
	        double[][] pBest		 = new double[swarm_size ][D], 
	                   R             = new double[swarm_size ][D],
	                   V             = new double[swarm_size ][D];
					   
					   
					   
					   
			vMax = wmax_coef * (UB-LB); 
			vMin = -vMax; 
	        
	        Random rand = new Random();
			
			//Konum ve hýz deðerlerini test amaclý ekrana yazdýrýyorum
			double  particles_v = vMin + Math.random()*(swarm_size - D ) + D  * ( vMax - vMin); 		
			System.out.println(" V degeri: " + particles_v); 		
			
			//Random  deðerleri 10 ile 5 arasýnda olacak þekilde üretiyorum. 
			double  particles_x = LB + Math.random()* (swarm_size - D) + D * (UB-LB); 		
			System.out.println("X degeri: " + particles_x);
	            
				
				
	        for(int p=0; p<swarm_size ; p++){
	            pBestValue[p] = nInfinite; 
	        }

	        for(int p=0; p<swarm_size ; p++){  
	            for(int i=0; i<D; i++){
	                R[p][i] = LB   + (UB-LB)*Math.random()*(swarm_size - D ) + D;
	                V[p][i] = vMin + (vMax-vMin)*Math.random()*(swarm_size - D ) + D;

	                if(rand.nextDouble() < 0.5){
	                    V[p][i] = -V[p][i];
	                    R[p][i] = -R[p][i];
	                }
	            }
	        }
	    
	        for(int p=0; p<swarm_size ; p++){
	            M[p] = fitness(R[p]);
	            M[p] = -M[p];
	        }
	        
	        for(int j=0; j<iter; j++){ 
	            for(int p=0; p<swarm_size ;p++){         
	                for(int i=0; i<D; i++){    
	                    R[p][i] = R[p][i] + V[p][i];
	                    
	                    //Parçacýklarýn çalýþma sýnýrýný(alanýný)belirleme. 
	                    
	                    if(R[p][i] > UB)            { R[p][i] = UB;}
	                    else if(R[p][i] < LB)       { R[p][i] = LB;}
						else if (R[p][i]  > vMax) 	{ R[p][i] =vMax;}
						else if (R[p][i]  < vMin) 	{ R[p][i] = vMin;}	
						
	                }         
	            }   

	            for(int p=0; p<swarm_size ; p++){ 

	                M[p] = fitness(R[p]);
	                M[p] = -M[p];
	            
	                if(M[p] > pBestValue[p]){
	                
	                     pBestValue[p] = M[p];
	                     for(int i=0; i<D; i++){
	                        pBest[p][i] = R[p][i];
	                     }
	                 }
	            
	                if(M[p] > gBestValue){
	        
	                    gBestValue = M[p];          
	                    for(int i=0; i<D; i++){
	                       gBest[i] =  R[p][i];
	                    }
	                }
	            
	            }
	            fitnes_fun[j] = gBestValue;
	        
	            
	            for(int p=0; p<swarm_size; p++){
	                for(int i=0; i<D; i++){
	                    
	                    r1 = rand.nextDouble();
	                    r2 = rand.nextDouble();
	                    V[p][i] = w * (V[p][i] + r1 * c1 * (pBest[p][i] - R[p][i]) + r2*c2 *(gBest[i] - R[p][i]));
	                
	                    // Klasik hýz güncelleme Formülü.              
	                    if      (V[p][i] > vMax) { V[p][i] = vMax; }        
	                    else if (V[p][i] < vMin) { V[p][i] = vMin; }
	                }
	            }
	            //Hýz Güncelleme Formüllü.
	            System.out.println("iterasyon: " + j + " BestDeðeri " + gBestValue);
	        }   
	    }
		

}
