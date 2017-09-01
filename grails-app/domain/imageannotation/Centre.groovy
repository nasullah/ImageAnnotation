package imageannotation

class Centre {

    static auditable = true
    static constraints = {
        centreName()
    }

    String centreName

    /*
     * Methods of the Domain Class
     */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "${centreName}";
    }
}
