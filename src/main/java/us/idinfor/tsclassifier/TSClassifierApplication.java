package us.idinfor.tsclassifier;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.opencv.ml.CvSVM;

import us.idinfor.tsclassifier.resources.TSClassifierResource;

public class TSClassifierApplication extends Application<TSClassifierConfiguration> {
	
	public static CvSVM SVM;
	
	public static void main(String[] args) throws Exception{
		new TSClassifierApplication().run(args);
	}
	
	@Override
	public void initialize(Bootstrap<TSClassifierConfiguration> bootstrap) {
		nu.pattern.OpenCV.loadShared();
		SVM = new CvSVM();
		SVM.load("path");
		
	}

	@Override
	public void run(TSClassifierConfiguration configuration, Environment environment){
		final TSClassifierResource  resource = new TSClassifierResource();
		environment.jersey().register(resource);
		
	}

}
