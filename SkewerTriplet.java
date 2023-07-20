class SkewerTriplet {
    private Piece skewerThreat;
    private Piece myProtectedPiece;
    private int skewerDirection;

    SkewerTriplet(Piece skewerThreat, Piece myProtectedPiece, int skewerDirection) {
        this.skewerThreat = skewerThreat;
        this.myProtectedPiece = myProtectedPiece;
        this.skewerDirection = skewerDirection;
    }

    protected Piece getSkewerThreat() {
        return skewerThreat;
    }

    protected Piece getMyProtectedPiece() {
        return myProtectedPiece;
    }

    protected int getSkewerDirection() {
        return skewerDirection;
    }

}
