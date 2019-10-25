package imageannotation

class FocusStatus {

    static auditable = true

    static belongsTo = [multiplexImage:MultiplexImage]
    static mapping = {
        comment type: "text"
        diagnosisNameOther type: "text"
    }

    static constraints = {
        multiplexImage()
        comment(nullable: true, widget: 'textarea')
        status(nullable: true)
        focusNumber(nullable: true)
        expert(nullable: true)
        diagnosis(nullable: true)
        diagnosisNameOther(nullable: true)
        focusType(nullable: true)
        stainType(nullable: true)
        stainTypeNameOther(nullable: true)
    }

    String comment
    FocusStatusDesc status
    Integer focusNumber
    Expert expert
    Diagnosis diagnosis
    String diagnosisNameOther
    FocusType focusType
    StainType stainType
    String stainTypeNameOther

    /*
     * Methods of the Domain Class
     */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "Focus: ${status}, Id: ${id} ";
    }
}
