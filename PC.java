package a2_2001040205;

import utils.DomainConstraint;
import utils.DOpt;
import utils.OptType;
import utils.AttrRef;

import java.util.Vector;

import utils.NotPossibleException;

/**
 * @overview PC (personal computer) with the following attributes: model, year, manufacturer, and comps (components)
 * @attributes model            String
 * year		        Integer			int
 * manufacturer		String
 * comps			Set<String>
 * @object A typical PC <m,y,r,c> where model(m), year(y), manufacturer(r), comps(c)
 * @abstract_properties mutable(model)=true /\ optional(model)=false /\ length(model)=20 /\
 * mutable(year)=false /\ optional(year)=false /\ min(year)=1984 /\
 * mutable(manufacturer)=false /\ optional(manufacturer)=false /\ length(manufacturer)=15 /\
 * mutable(comps)=true /\ optional(comps)=false
 */
public class PC {
    // constants
    private static final int LENGTH_MODEL = 20;
    private static final int MIN_YEAR = 1984;
    private static final int LENGTH_MANUFACTURER = 15;

    @DomainConstraint(type = "String", mutable = true, optional = false, length = LENGTH_MODEL)
    private String model;

    @DomainConstraint(type = "Integer", mutable = false, optional = false, min = MIN_YEAR)
    private int year;

    @DomainConstraint(type = "String", mutable = false, optional = false, length = LENGTH_MANUFACTURER)
    private String manufacturer;

    @DomainConstraint(type = "Set<String>", mutable = true, optional = false)
    private Set<String> comps;

    /**
     * @effects if model, year, manufacturer, comps are valid
     * initialize this as <model, year, manufacturer, comps>
     * else
     * throws NotPossibleException
     */
    public PC(@AttrRef("model") String model,
              @AttrRef("year") int year,
              @AttrRef("manufacturer") String manufacturer,
              @AttrRef("comps") Set<String> comps) throws NotPossibleException {
        if (!validateModel(model)) {
            throw new NotPossibleException("PC.init: Invalid PC model: \"" + model + "\"");
        }
        this.model = model;

        if (!validateYear(year)) {
            throw new NotPossibleException("PC.init: Invalid PC year: \"" + year + "\"");
        }
        this.year = year;

        if (!validateManufacturer(manufacturer)) {
            throw new NotPossibleException("PC.init: Invalid PC manufacturer: \"" + manufacturer + "\"");
        }
        this.manufacturer = manufacturer;

        if (!validateComps(comps)) {
            throw new NotPossibleException("PC.init: Invalid PC comps: \"" + comps + "\"");
        }
        this.comps = new Set<>();

        Vector<String> str = comps.getElements();
        if (str != null) {
            for (String ele : str) {
                if (ele != null) {
                    this.comps.insert(ele);
                }
            }
        }
    }

    /**
     * @effects if model is valid
     * set this.model = model
     * return true
     * else
     * return false
     */
    @DOpt(type = OptType.Mutator)
    @AttrRef("model")
    public boolean setModel(String model) {
        if (validateModel(model)) {
            this.model = model;
            return true;
        } else {
            return false;
        }
    }

    /**
     * @modifies comps
     * @effects if inC is already in this
     * do nothing
     * else
     * add inC to this
     */
    @DOpt(type = OptType.MutatorAdd)
    @AttrRef("comps")
    public void insertComps(String inC) {
        if (!comps.isIn(inC) && inC != null) {
            comps.insert(inC);
        }
    }

    /**
     * @modifies comps
     * @effects if reC is not in this
     * do nothing
     * else
     * remove reC from this
     */
    @DOpt(type = OptType.MutatorRemove)
    @AttrRef("comps")
    public void removeComps(String reC) {
        if (comps.isIn(reC) && reC != null) {
            comps.remove(reC);
        }
    }

    /**
     * @modifies comps
     * @effects if setC is valid
     * comps = new Set<>
     * for all element ele of setC:
     * comps.insert(e)
     * else
     * do nothing
     */
    @DOpt(type = OptType.Mutator)
    @AttrRef("comps")
    public boolean setComps(Set<String> setC) {
        if (validateComps(setC)) {
            comps = new Set<>();
            Vector<String> newSet = setC.getElements();
            if (newSet != null) {
                for (String ele : newSet) {
                    if (ele != null) {
                        comps.insert(ele);
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * @effects return model
     */
    @DOpt(type = OptType.Observer)
    @AttrRef("model")
    public String getModel() {
        return model;
    }

    /**
     * @effects return year
     */
    @DOpt(type = OptType.Observer)
    @AttrRef("year")
    public int getYear() {
        return year;
    }

    /**
     * @effects return manufacturer
     */
    @DOpt(type = OptType.Observer)
    @AttrRef("manufacturer")
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * @effects return a new Set<String> of all elements of comps
     */
    @DOpt(type = OptType.Observer)
    public Set<String> getComps() {
        Set<String> s = new Set<>();
        Vector<String> vec = comps.getElements();
        if (vec != null) {
            for (String string : vec) {
                if (string != null) {
                    s.insert(string);
                }
            }
        }
        return s;
    }

    /**
     * @effects if model is valid
     * return true
     * else
     * return false
     */
    private boolean validateModel(String model) {
        return (model != null && model.length() > 0 && model.length() <= LENGTH_MODEL);
    }

    /**
     * @effects if year is valid
     * return true
     * else
     * return false
     */
    private boolean validateYear(int year) {
        return year >= MIN_YEAR;
    }

    /**
     * @effects if manufacturer is valid
     * return true
     * else
     * return false
     */
    private boolean validateManufacturer(String manufacturer) {
        return (manufacturer != null && manufacturer.length() > 0 && manufacturer.length() <= LENGTH_MANUFACTURER);
    }

    /**
     * @effects if this satisfies abstract properties
     * return true
     * else
     * return false
     */
    public boolean repOK() {
        return validateModel(model) && validateYear(year) && validateManufacturer(manufacturer) && validateComps(comps);
    }

    /**
     * @effects if comps is valid
     * return true
     * else
     * return false
     */
    private boolean validateComps(Set<String> comps) {
        return comps.repOK() && comps != null;
    }


    @Override
    public String toString() {
        return String.format("PC:<%s,%d,%s,%s>", model, year, manufacturer, comps);
    }

    /**
     * @effects determines equality of two PCs bases on their attributes
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof PC)) {
            return false;
        }

        PC pc = (PC) obj;
        return model.equals(pc.model) && year == pc.year && comps.equals(pc.comps) && manufacturer.equals(pc.manufacturer);
    }
}
