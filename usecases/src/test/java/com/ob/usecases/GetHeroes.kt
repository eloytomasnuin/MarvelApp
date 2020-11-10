import com.nhaarman.mockitokotlin2.verify
import com.ob.data.MarvelRepository
import com.ob.usecases.GetHeroes
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetHeroes {

    @Mock
    lateinit var repository: MarvelRepository

    lateinit var useCase: GetHeroes

    @Before
    fun setUp() {
        useCase = GetHeroes(repository)
    }

    @Test
    fun useCaseCallRepositoryWhenIsCalledWithTrueParam() {

        runBlocking {
            useCase(GetHeroes.Params(false))

            verify(repository).getHeroes(false)
        }
    }

    @Test
    fun useCaseCallRepositoryWhenIsCalledFalseParam() {

        runBlocking {
            useCase(GetHeroes.Params(true))

            verify(repository).getHeroes(true)
        }
    }
}