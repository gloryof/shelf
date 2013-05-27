package jp.glory.bookshelf.domain.book.service;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.common.expection.BusinessException;
import jp.glory.bookshelf.common.expection.DataNotFoundException;
import jp.glory.bookshelf.common.validate.ValidateErrors;
import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.repository.BookRepository;
import jp.glory.bookshelf.domain.book.rule.BookCreatingRule;
import jp.glory.bookshelf.domain.book.rule.BookListDeletingRule;
import jp.glory.bookshelf.domain.book.rule.BookUpdatingRule;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.book.value.IsbnCode;

import org.apache.commons.lang3.StringUtils;

/**
 * 本サービス
 * 
 * @author Junki Yamada
 * 
 */
public class BookService {

	/** リポジトリ */
	private final BookRepository repository;

	/**
	 * コンストラクタ
	 * 
	 * @param repository リポジトリ
	 */
	public BookService(final BookRepository repository) {

		this.repository = repository;
	}

	/**
	 * 本IDから本を取得する
	 * 
	 * @param bookId 本ID
	 * @return 本
	 */
	public Book findById(final BookId bookId) {

		if (bookId == null) {

			return null;
		}

		final Book book = repository.findById(bookId);

		if (book == null) {

			throw new DataNotFoundException(Book.LABEL);
		}

		return book;
	}

	/**
	 * ISBNから本リストを取得する
	 * 
	 * @param isbnCode ISBNコード
	 * @return 本リスト
	 */
	public List<Book> findListByIsbnCode(final IsbnCode isbnCode) {

		if (isbnCode == null) {

			return new ArrayList<>();
		}

		if (StringUtils.isEmpty(isbnCode.getOnlyCodes())) {

			return new ArrayList<>();
		}

		return repository.findListByIsbnCode(isbnCode);
	}

	/**
	 * 新しく本を作成する
	 * 
	 * @param book 本
	 * @return 本ID
	 * @throws BusinessException 入力チェックエラーがある場合、スローされる
	 */
	public BookId create(final Book book) throws BusinessException {

		final ValidateErrors errors = new BookCreatingRule(book).validate();
		if (errors.hasError()) {

			throw new BusinessException(errors);
		}

		final BookId newBookId = repository.createNewNumbering();
		repository.create(newBookId, book);

		return newBookId;
	}

	/**
	 * 本を更新する
	 * 
	 * @param book 本
	 * @throws BusinessException 入力チェックエラーがある場合、スローされる
	 */
	public void update(final Book book) throws BusinessException {

		final ValidateErrors errors = new BookUpdatingRule(book).validate();
		if (errors.hasError()) {

			throw new BusinessException(errors);
		}

		repository.update(book);
	}

	/**
	 * 本を削除する
	 * 
	 * @param bookIdList 本IDリスト
	 * @throws BusinessException 入力チェックエラーがある場合、スローされる
	 */
	public void deleteBooks(final List<BookId> bookIdList) throws BusinessException {

		final ValidateErrors errors = new BookListDeletingRule(bookIdList).validate();
		if (errors.hasError()) {

			throw new BusinessException(errors);
		}

		for (final BookId bookId : bookIdList) {

			repository.delete(bookId);
		}
	}
}
