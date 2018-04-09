package imageannotation

class Label {

    static auditable = true
    static mapping = {
        comment type: "text"
    }
    static belongsTo = [patch:Patch]
    static constraints = {
        labelName()
        labeler()
        comment(nullable: true)
    }

    String labelName
    Expert labeler
    String comment

    public String toString() {
        return "Label Name: ${labelName}, Labeler: ${labeler}";
    }
}
