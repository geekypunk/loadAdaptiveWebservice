package com.cs5412.filesystem.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import com.cs5412.filesystem.IFileSystem;
import com.cs5412.utils.ServerConstants;
/*
 * Implemented using Java NIO(Blocking)
 * Reason : http://stackoverflow.com/questions/1605332/java-nio-filechannel-versus-fileoutputstream-performance-usefulness
 * */
public class FileSystemImpl implements IFileSystem{

	@Override
	public void createFile(InputStream is,String fileName) throws IOException {
		// TODO Auto-generated method stub
		 
		 File storeFile = new File(fileName);
		 ReadableByteChannel rbc = Channels.newChannel(is);
		 FileOutputStream os =  new FileOutputStream(storeFile);
         FileChannel foc = os.getChannel();
         ByteBuffer buf = ByteBuffer.allocateDirect(ServerConstants.UPLOAD_BUFFER);
         while(rbc.read(buf)!= -1) {
             buf.flip();
             foc.write(buf);
             buf.clear();
         }
         os.close();
        
	}
	
	@Override
	public void createFile(String text,String fileName) throws IOException {
		PrintWriter out = new PrintWriter(fileName);
		out.println(text);
		out.close();
	}
	
	@Override
	public boolean deleteFile(String fileName) throws IOException {
		// TODO Auto-generated method stub
		Path p1 = Paths.get(getFilePath(fileName)+File.separator+fileName);
		return Files.deleteIfExists(p1);
	}

	@Override
	public Collection<File> getAllUploaded() {

		Collection<File> dir = FileUtils.listFiles(new File(ServerConstants.UPLOAD_DIRECTORY_ROOT),
		        TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		return dir;
		
	}
	@Override
	public Collection<File> getUploadedTrainingDatasets() {
		
		Collection<File> dir = FileUtils.listFiles(new File(ServerConstants.UPLOAD_DIRECTORY_TRAIN),
				TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		return dir;
		
	}
	@Override
	public Collection<File> getUploadedTestDatasets() {
		
		Collection<File> dir = FileUtils.listFiles(new File(ServerConstants.UPLOAD_DIRECTORY_TEST),
				TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		return dir;
		
	}

	@Override
	public String getFilePath(String fileName) {
		// TODO Auto-generated method stub
		String path = null;
		if(fileName.contains(".train")){
			path = ServerConstants.UPLOAD_DIRECTORY_TRAIN+File.separator+fileName;
		}
		else if(fileName.contains(".train")){
			path = ServerConstants.UPLOAD_DIRECTORY_TEST+File.separator+fileName;
		}
		else{
			path = ServerConstants.UPLOAD_DIRECTORY_OTHER+File.separator+fileName;
		}
			
		return path;
	}

	@Override
	public String readFileToString(String filePath) throws IOException {
		// TODO Auto-generated method stub
		return FileUtils.readFileToString(new File(filePath));
	}


	
}
