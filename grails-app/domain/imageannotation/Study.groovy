package imageannotation

class Study {

    static auditable = true
    static hasMany = [multiplexImages:MultiplexImage]
    static constraints = {
        studyName()
    }

    String studyName

    /*
    * Methods of the Domain Class
    */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "${studyName}";
    }
}
