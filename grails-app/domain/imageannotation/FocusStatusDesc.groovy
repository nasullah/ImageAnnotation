package imageannotation

class FocusStatusDesc {

    static auditable = true

    static mapping = {
        focusStatusDescName type: "text"
    }

    static constraints = {
        focusStatusDescName(nullable: true)
    }

    String focusStatusDescName

    /*
     * Methods of the Domain Class
     */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "${focusStatusDescName}";
    }
}
