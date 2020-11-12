package com.geeboo.shiroito.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @author hongyq (修改代码请联系开发者)
 * @date 2020-11-11 14:20
 */
public class ParamValueUtils {

    private static Random random = new Random();

    public static String valueGenerate(String name, String className) {

        if(className.equals("java.lang.Long") || className.equals("long")) {

            if(name.equals("current")) {
                return "1";
            }

            if(name.equals("size")) {
                return "10";
            }

            return random.nextInt(1000000000) + "";
        }
        else if(className.equals("java.lang.String")) {
            return name + random.nextInt(10);
        }
        else if(className.equals("java.lang.Double") || className.equals("double")) {

            return random.nextInt(1000000000) + "";
        }else if(className.equals("java.lang.Float") || className.equals("float")) {

            return random.nextInt(1000000000) + "";
        }
        else if(className.equals("java.lang.Integer") || className.equals("int")) {

            if(name.equals("current")) {
                return "1";
            }

            if(name.equals("size")) {
                return "10";
            }

            return random.nextInt(1000000000) + "";
        }
        else if(className.equals("java.lang.Boolean") || className.equals("boolean")) {

            int i = random.nextInt(10);
            if(i > i/2) {
                return "true";
            }
            return "false";
        }
        else if(className.startsWith("java.util.List")) {

            return random.nextInt(1000000000) + "," + random.nextInt(1000000000);
        }
        else if(className.equals("java.util.Date") || className.equals("java.time.LocalDateTime")) {
            return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        }

        return "";
    }

}
