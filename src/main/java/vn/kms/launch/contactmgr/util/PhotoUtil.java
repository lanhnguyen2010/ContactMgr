package vn.kms.launch.contactmgr.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhotoUtil {
    private static final Logger LOG = LoggerFactory.getLogger(PhotoUtil.class);
    
    private PhotoUtil(){
        
    }
    public static void storeFile(InputStream in, String pathFull ) throws Exception{
        
        LOG.debug("Begin store file {}:", pathFull);
        
        File out = new File(pathFull);
        try {
//            FileUtils.copyInputStreamToFile(in, out);
            LOG.debug("End store file {}:", pathFull);
        } catch (Exception e) {
            LOG.debug("Can not store file {}:", pathFull);
            throw new Exception("Error when store file"+ e.getMessage());
            // TODO: handle exception
        }
    }
    
    /*
     * @param path
     * @return
     * @throw FileNotFoundException*/
    public static FileInputStream getFile(String path) throws Exception {
        
        LOG.debug("Get file {}:", path);
        
        try {
            
            return new FileInputStream(new File(path));
        } catch (Exception e) {
            LOG.debug("Cannot get file{}:",path);
            throw new Exception("File not found"+ e.getMessage());
            // TODO: handle exception
        }    
    }

}
