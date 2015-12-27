package main.types;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;



public class TreasureOut extends PrintStream {
        private final PrintStream secondary;

        public TreasureOut(OutputStream main, java.io.PrintStream printStream) {
            super(main);
            this.secondary = printStream;
        }

        @Override
        public void flush() {
            super.flush();
            secondary.flush();
        }

        @Override
        public void write(byte[] buf, int off, int len) {
            super.write(buf, off, len);
            secondary.write(buf, off, len);
        }

        @Override
        public void write(int b) {
            super.write(b);
            secondary.write(b);
        }

        @Override
        public void write(byte[] b) throws IOException {
            super.write(b);
            secondary.write(b);
        }
    }