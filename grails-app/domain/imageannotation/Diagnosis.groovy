package imageannotation

class Diagnosis {

    static auditable = true

    static mapping = {
        diagnosisName type: "text"
    }

    static constraints = {
        diagnosisName(nullable: true)
    }

    String diagnosisName

    /*
     * Methods of the Domain Class
     */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "${diagnosisName}";
    }
}
