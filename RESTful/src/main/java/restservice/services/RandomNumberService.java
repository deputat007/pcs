package restservice.services;

import com.zeroisbiggerthanone.pcs.entities.RandomNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restservice.repositories.RandomNumberDao;
import restservice.services.core.IdGenerator;
import restservice.services.core.RandomGenerator;

@Service
public class RandomNumberService {

    private RandomNumberDao randomNumberDao;

    @Autowired
    public RandomNumberService(RandomNumberDao randomNumberDao) {
        this.randomNumberDao = randomNumberDao;
    }

    public int getNumber(int number) {
        try {
            int result = randomNumberDao.getNumber(number);
            randomNumberDao.delete(number);

            return result;
        } catch (Exception e) {
            return 0;
        }
    }

    public int generateNumber() {
        final RandomNumber randomNumber = new RandomNumber();
        final String id = IdGenerator.generateId();
        final int number = RandomGenerator.generate(RandomGenerator.DEFAULT_RANGE);

        randomNumber.setId(id);
        randomNumber.setNumber(number);

        randomNumberDao.insert(randomNumber);

        return number;
    }
}
