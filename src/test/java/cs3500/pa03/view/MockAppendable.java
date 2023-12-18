package cs3500.pa03.view;

import java.io.IOException;

/**
 * Represents a mock appendable
 */
public class MockAppendable {

  static Appendable appendable = new Appendable() {
    @Override
    public Appendable append(CharSequence csq) throws IOException {
      throw new IOException("Mocked IOException");
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
      throw new IOException("Mocked IOException");
    }

    @Override
    public Appendable append(char c) throws IOException {
      throw new IOException("Mocked IOException");
    }
  };
}

