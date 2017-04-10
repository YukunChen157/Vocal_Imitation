/*********************************************************
 *	File:			CosineDistance.java
 *	Date:			12/24/2016
 *	Author:			Yukun Chen
 *
 *	Description:	This method can return the cosine
 *					distance of two float vectors.
 ********************************************************/

public class CosineDistance {
	public static float getCosionDistance(float[] vectorA, float[] vectorB){
		float cosDist = 0;
		float AB=0, Asquare=0, Bsquare=0;
		for(int i=0; i<vectorA.length; i++){
			AB += vectorA[i]*vectorB[i];
			Asquare += Math.pow(vectorA[i], 2);
			Bsquare += Math.pow(vectorB[i], 2);
		}
		cosDist = (float) (AB/Math.pow(Asquare, 0.5)/Math.pow(Bsquare, 0.5));
		return cosDist;
	}
}
