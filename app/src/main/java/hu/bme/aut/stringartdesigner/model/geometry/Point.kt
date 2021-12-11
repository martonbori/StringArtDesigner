package hu.bme.aut.stringartdesigner.model.geometry

data class Point (
    var pos: Position,
    var edge: Int,
    var n: Int
) {
    constructor(x:Float, y:Float, edge:Int = -1, num:Int = -1) : this(Position(x,y),edge,num)
}