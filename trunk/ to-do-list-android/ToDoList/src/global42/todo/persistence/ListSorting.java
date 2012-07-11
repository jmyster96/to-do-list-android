package global42.todo.persistence;

public enum ListSorting {
	DateOfCreation(0),
	Priority(1);
//	DueDate(),

	private int id;
	
	private ListSorting(int id){
		this.id = id;
	}
	public int getId() {
		return this.id;
	}
	
	public static ListSorting getListSortingFor(int id){
		ListSorting returnVal = DateOfCreation;
		for (ListSorting sorting : ListSorting.values()) {
			if (sorting.getId() == id) {
				return sorting;
			}
		}
		return returnVal;
	}
}
