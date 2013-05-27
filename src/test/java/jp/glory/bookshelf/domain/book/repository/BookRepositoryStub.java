package jp.glory.bookshelf.domain.book.repository;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.book.value.IsbnCode;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;

import org.apache.commons.lang3.StringUtils;

public class BookRepositoryStub implements BookRepository {

	private long numberingNumber = 0;

	private final List<Book> bookList = new ArrayList<>();

	@Override
	public void create(final BookId bookId, final Book book) {

		final Book newBook = new Book(bookId);
		newBook.setIsbnCode(book.getIsbnCode());
		newBook.setPrice(book.getPrice());
		newBook.setTitle(book.getTitle());
		newBook.setParentShelfId(book.getParentShelfId());

		bookList.add(newBook);
	}

	@Override
	public void update(final Book book) {

		for (int i = 0; i < bookList.size(); i++) {

			final Book base = bookList.get(i);

			if (base.getBookId().isSame(book.getBookId())) {

				bookList.set(i, book);
				break;
			}
		}
	}

	@Override
	public void delete(final BookId bookId) {

		final int index = getListIndex(bookId);

		if (index < 0) {

			return;
		}

		bookList.remove(index);
	}

	@Override
	public Book findById(final BookId bookId) {

		for (final Book book : bookList) {

			if (book.getBookId().isSame(bookId)) {

				return book;
			}
		}

		return null;
	}

	@Override
	public List<Book> findListByShelfId(final ShelfId shelfId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public BookId createNewNumbering() {

		final BookId bookId = new BookId(numberingNumber);

		numberingNumber++;

		return bookId;
	}

	@Override
	public List<Book> findListByIsbnCode(final IsbnCode isbnCode) {

		final List<Book> returnList = new ArrayList<>();
		for (final Book book : bookList) {

			if (StringUtils.equals(book.getIsbnCode().getOnlyCodes(), isbnCode.getOnlyCodes())) {

				returnList.add(book);
			}
		}

		return returnList;
	}

	public void setCurrentNumber(final long numberingNumber) {

		this.numberingNumber = numberingNumber;
	}

	public long getCurrentNumber() {

		return numberingNumber;
	}

	public void addBook(final Book book) {

		bookList.add(book);
	}

	private int getListIndex(final BookId bookId) {

		int index = 0;
		for (final Book book : bookList) {

			if (bookId.isSame(book.getBookId())) {

				return index;
			}

			index++;
		}

		return -1;
	}
}
