package a2_1901040240;

import static utils.TextIO.getln;
import static utils.TextIO.getlnInt;
import static utils.TextIO.putln;
import static utils.TextIO.writeFile;
import static utils.TextIO.writeStandardOutput;

import java.util.Vector;

import utils.AttrRef;
import utils.DOpt;
import utils.DomainConstraint;
import utils.NotPossibleException;
import utils.OptType;

/**
 * @overview PCProg is a program that captures data about PC objects
 *    and displays a report about them on the console.
 *
 * @attributes
 *  objs  Set<PC>
 *
 * @object
 *  A typical PCProg is {c1,...,cn} where c1,...,cn are pcs
 *
 * @abstract_properties
 *  mutable(objs)=true /\ optional(objs)=false
 *
 */
public class PCProg {
  @DomainConstraint(mutable=true,optional=false)
  private Set<PC> objs;
  
  /**
   * @effects
   *  initialise this to have an empty set of PCs
   */
  public PCProg() {
    objs = new Set<>();
  }
  
  /**
   * @modifies objs
   * @effects uses PCFactory to create a new PC object and record it in objs
   */
  @DOpt(type=OptType.Mutator)
  @AttrRef("objs")
  public void createObjects() {
	  String choice ="Y";
	  do {
			putln("Enter PC's model:");
			String model = getln();
			
			putln("Enter PC's year:");
			int year = getlnInt();
			
			putln("Enter PC's manufacturer:");
			String manufacturer = getln();
			
			putln("Enter all PC's components:");
			String commpsInput = getln();
			
			String[] compsArr = commpsInput.split(",");
			Set<String> set = new Set<>();
			
			if (compsArr!=null) {
				for (String string : compsArr) {
					if (string!=null) {
						set.insert(string);
					}
				}
			}
			
			try {
				objs.insert(PCFactory.createPC(model, year, manufacturer, set));
			} catch (NotPossibleException e) {
				System.err.println("Cannot create the PC because of errors:");
				e.printStackTrace(System.err.printf(""));
			}
			
			do {
				putln("Do you want to continue (Please answer \"Y\" or \"N\"):");
                choice = getln();
			} while (!choice.equals("N") && !choice.equals("Y"));
			
		} while(!choice.equals("N"));
  }
  
  /**
   * @effects return a new Set<PC> of all elements of objs
   */
  @DOpt(type=OptType.Observer)
  public Set<PC> getObjects() {
	  Set<PC> setPc = new Set<>();
	  Vector<PC> vec = objs.getElements();
	  if (vec!=null) {
		  for (PC el : vec) {
			  if (el!=null) {
				  setPc.insert(el);
			  }
		  }
	  }
	  return setPc;
  }

    /**
     * @effects
     *  if objs is not empty
     *    display to the standard console a text-based tabular report on objs
     *    return this report
     *  else
     *    display nothing and return null
     */
  public String displayReport() {
    if (objs.size() > 0) {
      Vector<PC> pcs = objs.getElements();
      PCReport reportObj = new PCReport();
      return reportObj.displayReport(pcs.toArray(new PC[pcs.size()]));
    } else {
      return null;
    }
  }

    /**
     * @effects
     *  save report to a file pcs.txt in the same directory
     *  as the program's
     */
  public void saveReport(String report) {
    String fileName = "pcs.txt";
    writeFile(fileName);
    putln(report);
    writeStandardOutput();
  }

    /**
     * The run method
     * @effects
     *  initialise an instance of PCProg
     *  create objects from data entered by the user
     *  display a report on the objects
     *  prompt user to save report to file
     *  if user answers "Y"
     *    save report
     *  else
     *    end
     */
  public static void main(String[] args) {
    //
    PCProg prog = new PCProg();
    
    // create objects
    try {
      prog.createObjects();
    
      // display report
      String report = prog.displayReport();
        
      if (report != null) {
        // prompt user to save report
        putln("Save report to file? [Y/N]");
        String toSave = getln();
        if (toSave.equals("Y")) {
          prog.saveReport(report);
          putln("report saved");
        }
      }
    } catch (NotPossibleException e) {
      System.err.printf("%s: %s%n", e, e.getMessage());
    }
    
    putln("~END~");
  }
}