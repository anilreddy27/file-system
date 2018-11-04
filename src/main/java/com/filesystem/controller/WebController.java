package com.filesystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import com.google.common.net.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("/")
public class WebController {

    @Autowired
    private Environment environment;

    @Value("${filesystem.document.file.path}")
    private String filePath;

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @RequestMapping("/getAllFiles")
    public @ResponseBody Set<String> getAllFiles() {

        Set<String> set = new HashSet<>();
        try {
            File fileDirectory = new File(filePath);
            if (!fileDirectory.exists()) {
                    throw new Exception("Unable to create folder at " + fileDirectory.getAbsolutePath());
            }

            File[] files = fileDirectory.listFiles();
            int i=0;
            for (File file : files) {
                if (file.isFile()) {
                    set.add(file.getName());
                }
            }
        } catch (Exception e) {
            set.add(e.getMessage());
        }
        return set;
    }

    @RequestMapping("/downloadFile")
    public @ResponseBody Map<String, String> downloadFile(@RequestParam String fileName,HttpServletResponse httpResponse) {

        Map<String, String> map = new HashMap<>();
        try {
            File fileDirectory = new File(filePath);
            if (!fileDirectory.exists()) {
                throw new Exception("Unable to create folder at " + fileDirectory.getAbsolutePath());
            }

            Path path = Paths.get(filePath+File.separator+fileName);
            byte[] b = Files.readAllBytes(path);
            httpResponse.setContentLength(b.length);
            httpResponse.setContentType(MediaType.PDF.toString());
            httpResponse.getOutputStream().write(b);

            Path pathDestination = Paths.get(filePath+File.separator+"_anil"+fileName);
            byte[] bytea = Files.readAllBytes(path);
            Files.write(pathDestination,bytea);

        } catch (Exception e) {
            map.put("errorMessage", "Unable to process pdf file.");
        }
        return map;
    }
}
