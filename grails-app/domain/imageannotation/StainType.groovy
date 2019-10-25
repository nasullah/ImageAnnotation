package imageannotation

class StainType {

    static auditable = true

    static mapping = {
        stainTypeName type: "text"
    }

    static constraints = {
        stainTypeName(nullable: true)
    }

    String stainTypeName

    /*
     * Methods of the Domain Class
     */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "${stainTypeName}";
    }
}
