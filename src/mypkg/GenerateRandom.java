package mypkg;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class GenerateRandom {
    public static void generateRandom(int n)
    {
        Set<Integer> s = new LinkedHashSet<>(n);
        Random random = new Random(System.currentTimeMillis());
        while(s.size() < n) {
            int i = random.nextInt(10000000) - 5000000;
            s.add(i);
        }
        try{
            File file = new File("myRandom.txt");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            Object[] objects = s.toArray();
            for(Object obj : objects)
            {
                bufferedWriter.write(obj.toString() + " ");
            }
            bufferedWriter.write("\n");
            bufferedWriter.close();
            fileWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
