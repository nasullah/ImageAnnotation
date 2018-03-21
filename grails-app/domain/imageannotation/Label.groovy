package imageannotation

class Label {

    static auditable = true
    static belongsTo = [patch:Patch]
    static constraints = {
        labelName()
        labeler()
    }

    String labelName
    Expert labeler

    public String toString() {
        return "Label Name: ${labelName}, Labeler: ${labeler}";
    }
}
