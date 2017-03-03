package uk.co.bristlecone.voltdb.wrapgen.console;

public class ConsoleWrapgenMain {
  private ConsoleWrapgenMain() {
    // intentionally empty
  }

  public static void main(String[] args) {
    new ConsoleWrapgenController().run(args);
  }
}
