import java.util.Scanner
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.MutableList
import collection.mutable.HashMap

object markov {
	 
	def main(args: Array[String]){
	  if (args.length < 2){
	    System.out.println("I'm using java in scala - and you need to provide runtime arguments")
	    System.exit(-1)
	  }
	  
	  //Parse the input file into tokens (strings) separated by whitespace. Scanner is slow but it's nostalgic to use it.
	  val scanner = new Scanner(io.Source.fromFile(args(0)).mkString.toLowerCase)
	  val list = new ArrayBuffer[String]
	  while(scanner.hasNext()){
	    list += scanner.next
	  }
	  
	  var nGrams = new MutableList[MutableList[String]]
	  
	  //scalas ternary operator
	  val n = if(args.length == 3) args(2).toInt else 3
	  
	  //create the n-grams
	  //outer loop creates list.length number of shingles
	  for (i <- 0 until list.length){
	    //inner loop creates each individual shingle of length n
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
	  
	  //place the n-grams in a hashmap with a key of length n-1 and the value is the last item in shingle
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
	 
	  //output the first part of the babble
	  val random = scala.util.Random
	  val first = nGrams(random.nextInt()%list.length)
	  print(first(n-1).toString + " ")
	  
	  //And the rest of the babble
	  var key = first.drop(1)
	  for (i <- 1 until args(1).toInt){
	    //lookup the key in the map
	    val wordList = map.apply(key)
	    
	    //get a random word for the list
	    val word = wordList(random.nextInt()%wordList.length)
	    
	    //print it out
	    print(word.toString() + " ")
	    
	    //reset the key for the next round
	    key = key.drop(1) ++ List(word)
	  }
	}	

}