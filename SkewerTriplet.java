class SkewerTriplet {
    private Piece skewerThreat;
    private Piece skeweredPiece;
    private int skewerDirection;

    SkewerTriplet(Piece skewerThreat, Piece myProtectedPiece, int skewerDirection) {
        this.skewerThreat = skewerThreat;
        this.skeweredPiece = myProtectedPiece;
        this.skewerDirection = skewerDirection;
    }

    protected Piece getSkewerThreat() {
        return skewerThreat;
    }

    protected Piece getSkeweredPiece() {
        return skeweredPiece;
    }

    protected int getSkewerDirection() {
        return skewerDirection;
    }

}
