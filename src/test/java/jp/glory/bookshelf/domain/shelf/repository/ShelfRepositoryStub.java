package jp.glory.bookshelf.domain.shelf.repository;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;

public class ShelfRepositoryStub implements ShelfRepository {

	private long numberingNumber = 0;

	private final List<Shelf> shelfList = new ArrayList<>();

	@Override
	public void create(final ShelfId newId, final Shelf shelf) {

		final Shelf newShelf = new Shelf(newId, shelf.getBookList());
		newShelf.setName(shelf.getName());

		shelfList.add(newShelf);
	}

	@Override
	public ShelfId createNewNumbering() {

		final ShelfId shelfId = new ShelfId(numberingNumber);

		numberingNumber++;

		return shelfId;
	}

	@Override
	public void update(final Shelf shelf) {

		for (int i = 0; i < shelfList.size(); i++) {

			final Shelf base = shelfList.get(i);

			if (base.getShelfId().getValue() == shelf.getShelfId().getValue()) {

				shelfList.set(i, shelf);
				break;
			}
		}
	}

	@Override
	public void delete(final ShelfId shelfId) {
		for (int i = 0; i < shelfList.size(); i++) {

			final Shelf base = shelfList.get(i);

			if (base.getShelfId().getValue() == shelfId.getValue()) {

				shelfList.remove(i);
				break;
			}
		}

	}

	@Override
	public Shelf findById(final ShelfId id) {

		for (final Shelf shelf : shelfList) {

			if (shelf.getShelfId().getValue() == id.getValue()) {

				return shelf;
			}
		}

		return null;
	}

	@Override
	public List<Shelf> findAll() {

		return shelfList;
	}

	@Override
	public ShelfId findIdByBookId(final BookId bookId) {

		for (final Shelf shelf : shelfList) {

			final Book book = shelf.getBook(bookId);

			if (book != null) {

				return shelf.getShelfId();
			}
		}

		return null;
	}

	@Override
	public void addBook(final ShelfId shelfId, final BookId bookId) {

		final Shelf shelf = findById(shelfId);
		final Book book = new Book(bookId);
		book.setParentShelfId(shelfId);

		shelf.add(book);
	}

	@Override
	public void updateBooks(final Shelf shelf) {

		update(shelf);
	}

	public void setCurrentNumber(final long numberingNumber) {

		this.numberingNumber = numberingNumber;
	}

	public long getCurrentNumber() {

		return numberingNumber;
	}

	public void addShelf(final Shelf shelf) {

		shelfList.add(shelf);
	}

	@Override
	public void removeAllBooks(final ShelfId shelfId) {

		final Shelf shelf = findById(shelfId);

		final List<BookId> bookIdList = shelf.getBookIdList();
		for (final BookId bookId : bookIdList) {

			shelf.remove(bookId);
		}
	}

}
