import java.util.Scanner
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.MutableList
import collection.mutable.HashMap

object markov {
	 
	def main(args: Array[String]){
	  val scanner = new Scanner(io.Source.fromFile("ibs.txt").mkString.toLowerCase)
	  //val string = "Three blind mice Three blind cats Go to the store"
	  //val scanner = new Scanner(string)
	  val list = new ArrayBuffer[String]
	  while(scanner.hasNext()){
	    list += scanner.next
	  }
	  
	  var nGrams = new MutableList[MutableList[String]]
	  val n = 3
	  //out loop creates list.length number of shingles
	  for (i <- 0 until list.length){
	    //inner loop creates each individual shingles of length n
		var shingle = new MutableList[String]  
	    for (j <- 0 until n){
		    if (i+j >= list.length){
		    	shingle += list(i+j - list.length)
		    }
		    else{
		      shingle += list(i+j)
		    }
		 }
		nGrams += shingle
	  }
	  
	  val map = new HashMap[MutableList[String],MutableList[String]]
	  for (i <- nGrams){
		  val key = i.take(n-1)
	      if (map.contains(key)){
	        var tmpNGram = map.apply(key)
	    	 tmpNGram = tmpNGram ++ i.drop(n-1)
	    	 map.update(key, tmpNGram)
	      }
	      else{
	          map.update(key, i.drop(n-1))
	      }
	  }
	  
	  for(i <- map){
	    println(i)
	  }
	}	

}