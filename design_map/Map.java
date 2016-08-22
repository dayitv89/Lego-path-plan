/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Rajesh Alawa
 */
public class Map {
    public static void main(String arg[]){
        
        String csvFile = "data.txt";
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
        String[] country = null;
        
 
	try {
 
		br = new BufferedReader(new FileReader(csvFile));
		while ((line = br.readLine()) != null) {
		        // use comma as separator
			country = line.split(cvsSplitBy); 
		}
                
 
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
        String mapCordinates = "";
        for (int i = 0; i < country.length;i+=2){
//            Integer x = Integer.parseInt(country[i].trim(),16);
//            Integer y = Integer.parseInt(country[i+1].trim(),16);
//            mapCordinates += "["+x.toString()+","+y.toString()+"],";
            mapCordinates += "["+country[i]+","+country[i+1]+"],";
        }
        
        
        String header = "﻿<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n" +
"<html>\n" +
"<head>\n" +
"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
"    <title>Keep axes ratio</title>\n" +
"    <link href=\"lib/examples.css\" rel=\"stylesheet\" type=\"text/css\">\n" +
"    <style type=\"text/css\">\n" +
"        #content > div.middleAlign {\n" +
"            vertical-align: middle;\n" +
"            line-height: 1em;\n" +
"            height: 1em;\n" +
"        }\n" +
"    </style>\n" +
"    <!-- flot -->\n" +
"    <!--[if lte IE 8]><script language=\"javascript\" type=\"text/javascript\" src=\"../lib/excanvas.js\"></script><![endif]-->\n" +
"    <script language=\"javascript\" type=\"text/javascript\" src=\"lib/jquery.js\"></script>\n" +
"    <script language=\"javascript\" type=\"text/javascript\" src=\"lib/jquery.colorhelpers.js\"></script>\n" +
"    <script language=\"javascript\" type=\"text/javascript\" src=\"lib/jquery.flot.js\"></script>\n" +
"    <script language=\"javascript\" type=\"text/javascript\" src=\"lib/jquery.flot.navigate.js\"></script>\n" +
"	\n" +
"    <!-- this plugin -->\n" +
"    <script language=\"javascript\" type=\"text/javascript\" src=\"lib/jquery.flot.coordinate.js\"></script>\n" +
"    <script type=\"text/javascript\">\n" +
"\n" +
"        $(function () {\n" +
"            var data = [\n" +
"                [";
        String footer = "]\n" +
"            ];\n" +
"\n" +
"            var options = {\n" +
"                grid: {\n" +
"                    show: true,\n" +
"                    borderColor: \"#000\",\n" +
"                    borderWidth: 0,\n" +
"                    aboveData: false\n" +
"                },\n" +
"                coordinate: {\n" +
"                    type: \"auto\",\n" +
"                    ratioXY: 1\n" +
"                },\n" +
"                zoom: {\n" +
"                    interactive: true\n" +
"                },\n" +
"                pan: {\n" +
"                    interactive: true\n" +
"                },\n" +
"                xaxis: {\n" +
"                    min: -10,\n" +
"                    max: 10\n" +
"                },\n" +
"                yaxis: {\n" +
"                    min: -20,\n" +
"                    max: 20\n" +
"                }\n" +
"            };\n" +
"\n" +
"            $(document).ready(function () {\n" +
"                $.plot(\"#canvas-wrapper\", data, options);\n" +
"                \n" +
"                $(\"input[type=radio]\").change(function (event) {\n" +
"                    var values = [];\n" +
"                    $.each($(\"input[type=radio]\"), function (index, chk) {\n" +
"                        if ($(chk).attr(\"checked\")) {\n" +
"                            values.push($(chk).attr(\"value\"));\n" +
"                        }\n" +
"                    });\n" +
"\n" +
"                    options.coordinate.ratioXY = eval(values.join(\"\"));\n" +
"\n" +
"                    $.plot(\"#canvas-wrapper\", data, options);\n" +
"                });\n" +
"            });\n" +
"        });\n" +
"\n" +
"    </script>\n" +
"</head>\n" +
"<body>\n" +
"\n" +
"    <div id=\"header\">\n" +
"        <h2>Sewerage robot path</h2>\n" +
"    </div>\n" +
"\n" +
"    <div id=\"content\">\n" +
"        <div class=\"demo-container\">\n" +
"            <div id=\"canvas-wrapper\" class=\"demo-placeholder\"></div>\n" +
"        </div>\n" +
"        \n" +
"        \n" +
"    </div>\n" +
"\n" +
"    \n" +
"\n" +
"</body>\n" +
"</html>";
        
//        System.out.println(outputDecimal);
        
        try {
 
			File file = new File("map/index.html");
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(header);
			bw.write(mapCordinates);
			bw.write(footer);
			bw.close();
 
			System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
