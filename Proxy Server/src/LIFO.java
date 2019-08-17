import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mehdi Raza Rajani
 */
public class LIFO {
    int cacheCapacity = 3;
    int cacheHit = 0;
    int cacheMiss = 0;
    
    Stack<WebFile> cache = new Stack<>();
    Map <String,Integer> map = new HashMap<>();

    public WebFile add(String URL){
        if (map.containsKey(URL)){
            Stack<WebFile> tempStack = new Stack<>();
            if (map.get(URL) != 0)
                for (int i = 0; i < map.get(URL) - 1; i++)
                    tempStack.add(cache.pop());
            WebFile current = cache.pop();
            while (!tempStack.isEmpty())
                cache.add(tempStack.pop());
            current.occ++;
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",Locale.US);
            current.lastHitDate = dateFormat.format(new Date());
            cache.add(current);
            map.put(URL, cache.size()-1);
            cacheHit++;
            return current;
        }
        if (cache.size() == cacheCapacity)
            map.remove(cache.pop().url);
        WebFile temp = new WebFile(URL);
        if (!temp.body.get(0).startsWith("MalformedURLException:") && !temp.body.get(0).startsWith("UnknownHostException:") && !temp.body.get(0).startsWith("IOException")){
            cache.add(new WebFile(URL));
            map.put(URL,cache.size()-1);          
            cacheMiss++;
        } else if (temp.body.get(0).startsWith("MalformedURLException:")){

        } else if (temp.body.get(0).startsWith("UnknownHostException:")){

        } else if (temp.body.get(0).startsWith("IOException")){
            
        }
        return temp;        
    }
    
    public void display(){
        Iterator iterate_value = cache.iterator();
        double total = cacheHit + cacheMiss;
        System.out.println("Cache Hit Rate: " + (cacheHit/total));
        System.out.println("Cache Miss Rate: " + (cacheMiss/total));
        System.out.println("Current Cache Item:");
        while (iterate_value.hasNext())
            System.out.println(iterate_value.next());
        System.out.println("------------------");
    }

}
