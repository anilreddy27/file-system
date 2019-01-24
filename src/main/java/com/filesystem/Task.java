package com.filesystem;

import java.util.concurrent.ThreadLocalRandom;

public class Task {

        public static void main(String args[]) {

                for(int i=0;i<3001;i++)
                {
                        String model = "";
                        if(i<1000)
                        {
                           model = model.concat("A1");
                        }else if(i<2000 && i> 1000)
                        {
                                model = model.concat("B1");
                        }else if(i<2500 && i> 2000)
                        {
                                model = model.concat("C1");
                        }else if(i<3001 && i> 2500)
                        {
                                model = model.concat("D1");
                        }
                        int j = 50000+i;
                        double lat = ThreadLocalRandom.current().nextDouble(100, 1000);

                        double lng = ThreadLocalRandom.current().nextDouble(100, 1000);

                        System.out.println("INSERT INTO `atm_locator` (`model`, `city`, `state`, `address`, `zip`, `lat`, `lng`) VALUES ('"+model+"','testCity"+i+"','testState"+i+"','testAddress"+i+"',"+j+","+String.format("%.5f", lat)+","+String.format("%.5f", lng)+");");
                }
        }

}



