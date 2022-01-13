package ir.kazemcodes.infinity.domain.use_cases.preferences

import ir.kazemcodes.infinity.domain.repository.Repository


class SaveParagraphDistanceUseCase(
    private val repository: Repository,
) {
    operator fun invoke(paragraphDistance: Int) {
        repository.preferencesHelper.paragraphDistance.set(paragraphDistance)
    }
}

class ReadParagraphDistanceUseCase(
    private val repository: Repository,
) {
    operator fun invoke(): Int {
        return repository.preferencesHelper.paragraphDistance.get()
    }
}
class SaveParagraphIndentUseCase(
    private val repository: Repository,
) {
    operator fun invoke(paragraphIndent: Int) {
        repository.preferencesHelper.paragraphIndent.set(paragraphIndent)
    }
}

class ReadParagraphIndentUseCase(
    private val repository: Repository,
) {
    operator fun invoke(): Int {
        return repository.preferencesHelper.paragraphIndent.get()
    }
}
