package edu.brown.cs320.student;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import edu.brown.cs320.student.csv.Searcher;


public class SearchClassTest2 {

  List<List<String>> expected = new ArrayList<>();
  List<String> row1 = new ArrayList<>(Arrays.asList("Rihanna", "Umbrella"));
  List<String> row2 = new ArrayList<>(Arrays.asList("J Balvin", "Bad Bunny"));
  List<String> row3 = new ArrayList<>(Arrays.asList("Justin Bieber", "Baby"));
  List<String> row4 = new ArrayList<>(Arrays.asList("Bad Bunny", "Bichiyal"));
  List<String> row5 = new ArrayList<>(Arrays.asList("Bad Bunny", "La Corriente"));

  List<List<String>>  rawRecords = new ArrayList(Arrays.asList(row1, row2, row3, row4, row5)) ;
  Searcher searcher = new Searcher(rawRecords) ;

  /**
   * User is looking for a word in a specified column
   */
  @Test
  public void SearchWithIndex() {

    String searchWord = "Rihanna";
    expected.add(row1);
    Integer columnIndex = 0;
    List<List<String>> actual = searcher.findString(searchWord, columnIndex);

    Assert.assertEquals(expected, actual);
  }

  /**
   * User is looking for a word in every column
   */
  @Test
  public void SearchWithoutIndex() {

    String searchWord = "Rihanna";
    expected.add(row1);

    List<List<String>> actual = searcher.findString(searchWord);

    Assert.assertEquals(expected, actual);
  }

  /**
   * User is looking for a composed word in a specified column
   */
  @Test
  public void SearchComposedWordWithIndex() {
    String searchWord = "Justin Bieber";
    expected.add(row3);
    Integer columnIndex = 0;
    List<List<String>> actual = searcher.findString(searchWord, columnIndex);

    Assert.assertEquals(expected, actual);
  }

  /**
   * User is looking for a composed word in every column
   */
  @Test
  public void SearchComposedWordWithoutIndex() {
    String searchWord = "Justin Bieber";
    expected.add(row3);
    List<List<String>> actual = searcher.findString(searchWord);
    Assert.assertEquals(expected, actual);
  }

  /**
   * User is looking for a word in a column where it does not exist
   */
  @Test
  public void SearchUnavailableEntryInColumn() {
    String searchWord = "Rihanna";
    Integer columnIndex = 1;
    List<List<String>> actual = searcher.findString(searchWord, columnIndex);
    Assert.assertEquals(expected, actual);
  }

  /**
   * User is looking for a word that does not exist in any column
   */
  @Test
  public void SearchUnavailableEntry() {
    String searchWord = "Anitta";
    List<List<String>> actual = searcher.findString(searchWord);
    Assert.assertEquals(expected, actual);
  }

  /**
   * User is looking for a word that exists in multiple rows (and same column)
   * User does not provide column index
   */
  @Test
  public void SearchMultipleEntriesWord() {
    String searchWord = "Bad Bunny";
    expected.add(row2);
    expected.add(row4);
    expected.add(row5);
    List<List<String>> actual = searcher.findString(searchWord);
    Assert.assertEquals(expected, actual);
  }

  /**
   * User is looking for a word that exists in multiple rows (and different column)
   * User does not provide column index
   */
  @Test
  public void SearchEntryInSeveralColumnsWithoutIndex() {
    String searchWord = "Bad Bunny";
    expected.add(row2);
    expected.add(row4);
    expected.add(row5);
    List<List<String>> actual = searcher.findString(searchWord);
    Assert.assertEquals(expected, actual);
  }

  /**
   * User is looking for a word that exists in multiple rows (and different column)
   * User provides column index
   */
  @Test
  public void SearchEntryInSeveralColumnsWithIndex() {
    String searchWord = "Bad Bunny";
    expected.add(row4);
    expected.add(row5);
    Integer columnIndex = 0;
    List<List<String>> actual = searcher.findString(searchWord, columnIndex);
    Assert.assertEquals(expected, actual);
  }

  /**
   * User is looking for a word but provides a column index that is higher than the number of
   * columns in the row
   */
  @Test
  public void SearchWithIndexTooHigh() {
    String searchWord = "Bad Bunny";
    Integer columnIndex = 6;
    List<List<String>> actual = searcher.findString(searchWord, columnIndex);
    Assert.assertEquals(expected, actual);
  }

  /**
   * User is looking for a word but provides a column index that is higher than the number of
   * columns in the row
   */
  @Test
  public void SearchWithNegativeIndex() {
    String searchWord = "Bad Bunny";
    Integer columnIndex = -10;
    expected.add(row2);
    expected.add(row4);
    expected.add(row5);

    List<List<String>> actual = searcher.findString(searchWord, columnIndex);
    Assert.assertEquals(expected, actual);
  }
}
