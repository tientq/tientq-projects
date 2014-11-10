package control;

class Node {
	Cell cell;
	int row;
	int column;
	Node next;
	Node prev;
	public Node(Cell cell, int row, int column) {
		this.cell = new Cell();
		this.cell.copyCell(cell);
		this.column = column;
		this.row = row;
	}
}