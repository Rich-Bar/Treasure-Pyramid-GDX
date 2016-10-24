package main.types;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;


public class TreasureOut extends PrintStream {
        private List<PrintStream> streams = new ArrayList<>();

        public TreasureOut(OutputStream main) {
            super(main);
        }

        public boolean addStream(PrintStream newStream){
        	return streams.add(newStream);
        }
        
        public boolean removeStream(PrintStream newStream){
        	return streams.remove(newStream);
        }
        
        @Override
        public void flush() {
            super.flush();
            for(PrintStream stream : streams)
            stream.flush();
        }
        
        @Override
        public void write(byte[] buf, int off, int len) {
            super.write(buf, off, len);
            for(PrintStream stream : streams)
            	stream.write(buf, off, len);
        }

        @Override
        public void write(int b) {
            super.write(b);
            for(PrintStream stream : streams)
            	stream.write(b);
        }

        @Override
        public void write(byte[] b) throws IOException { 
            super.write(b);
            for(PrintStream stream : streams)
            	stream.write(b);
        }
    }
