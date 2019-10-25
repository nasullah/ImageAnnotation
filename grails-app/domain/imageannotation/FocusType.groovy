package imageannotation

class FocusType {

    static auditable = true

    static mapping = {
        focusTypeName type: "text"
    }

    static constraints = {
        focusTypeName(nullable: true)
    }

    String focusTypeName

    /*
     * Methods of the Domain Class
     */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "${focusTypeName}";
    }
}
