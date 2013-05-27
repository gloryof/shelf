package jp.glory.bookshelf.domain.shelf.sevice;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.common.expection.DataNotFoundException;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.repository.ShelfRepository;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;

/**
 * 本棚参照サービス
 * 
 * @author Junki Yamada
 * 
 */
public class ShelfReferenceService {

	/** リポジトリ */
	private final ShelfRepository repository;

	/**
	 * コンストラクタ
	 * 
	 * @param repository リポジトリ
	 */
	public ShelfReferenceService(final ShelfRepository repository) {

		this.repository = repository;
	}

	/**
	 * IDをキーに本棚を取得する
	 * 
	 * @param shelfId 本棚ID
	 * @return 本棚
	 */
	public Shelf findById(final ShelfId shelfId) {

		final Shelf shelf = getShelf(shelfId);

		if (shelf == null) {

			throw new DataNotFoundException(Shelf.LABEL);
		}

		return shelf;
	}

	/**
	 * 全本棚リストを取得する
	 * 
	 * @return 全本棚リスト
	 */
	public List<Shelf> findAll() {

		return repository.findAll();
	}

	/**
	 * 対象の本棚が存在するかを判定する
	 * 
	 * @param shelfId 本棚ID
	 * @return 存在する場合：true、存在しない場合：false
	 */
	public boolean isExist(final ShelfId shelfId) {

		return (getShelf(shelfId) != null);
	}

	/**
	 * 移動対象本棚リストを作成する
	 * 
	 * @param shelfId 本棚ID
	 * @return 本棚リスト
	 */
	public List<Shelf> getMoveShelfLit(final ShelfId shelfId) {

		final List<Shelf> baseList = findAll();
		final List<Shelf> moveList = new ArrayList<>();

		for (final Shelf shelf : baseList) {

			if (!shelf.isSameShelf(shelfId)) {

				moveList.add(shelf);
			}
		}

		return moveList;
	}

	/**
	 * 本棚を取得する<br>
	 * 本棚が存在しない場合はNullを返却する。
	 * 
	 * @param shelfId 本棚ID
	 * @return 本棚
	 */
	private Shelf getShelf(final ShelfId shelfId) {

		if (shelfId == null) {

			return null;
		}

		final Shelf shelf = repository.findById(shelfId);

		return shelf;
	}
}
