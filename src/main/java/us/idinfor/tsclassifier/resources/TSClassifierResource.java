package us.idinfor.tsclassifier.resources;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.HOGDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.idinfor.tsclassifier.TSClassifierApplication;

import com.codahale.metrics.annotation.Timed;

@Path("/classify-ts")
@Produces(MediaType.APPLICATION_JSON)
public class TSClassifierResource {
    
    
    @GET
    @Timed
    @Path("/{country}/{name}")
    public Integer getClassificationClassId(
    		@PathParam(value = "country") String country,
    		@PathParam(value = "name") String name) throws URISyntaxException, IOException{
    	Logger logger = LoggerFactory.getLogger(TSClassifierResource.class);
    	
    	Integer res = -1;
    	
    	if(country.equalsIgnoreCase("germany") && !name.isEmpty()){
    		String imagePath = "path" + name;
    		File imageFile = new File(imagePath);
    		if(imageFile.exists()){
        		Mat img = Highgui.imread(imagePath, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
        		Imgproc.resize(img, img, new Size(40,40));
        		   
        		MatOfFloat descriptors = new MatOfFloat();
        		   
        		HOGDescriptor hog = new HOGDescriptor(new Size(40,40), new Size(8,8), new Size(4,4), new Size(4,4), 9);
        		hog.compute(img, descriptors);
        		res = (int)TSClassifierApplication.SVM.predict(descriptors);
        		logger.info("[Classification] name =  " + name + " -> class_id = " + res);
        		
    		}else{
    			logger.error("Image file does not exists");
    		}
    
    	}
    	
    	return res;
    }
    
    
}
