package com.Ciemiorek.Genderdetector.service;

import com.Ciemiorek.Genderdetector.common.ResponseDictionary;
import com.Ciemiorek.Genderdetector.common.TypeOfChecking;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

@Service
public class GenderDetectorService {
    File maleTokens = FileUtils.getFile("src/main/resources", "MaleTokens.txt");
    File femaleTokens = FileUtils.getFile("src/main/resources", "FemaleTokens.txt");

    public ResponseEntity<ResponseDictionary> checkGender(String fullName, TypeOfChecking firstOrAll) {
    if (firstOrAll.equals(TypeOfChecking.ALL)){return checkGenderByAllTokens(fullName);
    }else if(firstOrAll.equals(TypeOfChecking.FIRST)){return checkGenderByFirstToken(fullName);
        }else return new ResponseEntity(HttpStatus.BAD_REQUEST);

    }


    public ResponseEntity<ResponseDictionary> checkGenderByFirstToken(String fullName) {
        String firstName = fullName.split(" ")[0];

        if (isMale(firstName)) {
            return ResponseEntity.ok(ResponseDictionary.MALE);
        } else if (isFemale(firstName)) {
            return ResponseEntity.ok(ResponseDictionary.FEMALE);
        } else {
            return ResponseEntity.ok(ResponseDictionary.INCONCLUSIVE);
        }
    }


    public ResponseEntity<ResponseDictionary> checkGenderByAllTokens(String fullName) {
        List<String> namesList = Arrays.asList(fullName.split(" "));

        long maleTokensCounter = namesList.stream().filter(name -> isMale(name)).count();
        long femaleTokensCounter = namesList.stream().filter(name -> isFemale(name)).count();

        if (maleTokensCounter > femaleTokensCounter) {
            return ResponseEntity.ok(ResponseDictionary.MALE);
        } else if (femaleTokensCounter > maleTokensCounter) {
            return ResponseEntity.ok(ResponseDictionary.FEMALE);
        } else {
            return ResponseEntity.ok(ResponseDictionary.INCONCLUSIVE);
        }
    }


    private boolean isFemale(String name) {
        LineIterator it = null;
        try {
            it = FileUtils.lineIterator(femaleTokens, "UTF-8");
        } catch (final IOException e) {
            e.printStackTrace();
        }

        try {
            while (it.hasNext()) {
                String line = it.nextLine();
                name = name.toLowerCase();
                if (line.equals(name)) {
                    return true;
                }
            }
        } finally {
            LineIterator.closeQuietly(it);
        }
        return false;
    }

    private boolean isMale(String name) {
        LineIterator it = null;
        try {
            it = FileUtils.lineIterator(maleTokens, "UTF-8");
        } catch (final IOException e) {
            e.printStackTrace();
        }

        try {
            while (it.hasNext()) {
                String line = it.nextLine();
                name = name.toLowerCase();
                if (line.equals(name)) {
                    return true;
                }
            }
        } finally {
            LineIterator.closeQuietly(it);
        }
        return false;
    }

    public StreamingResponseBody returnAllNamesTokens() {
        return new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream outputStream) {
                try {
                    outputStream.write( FileUtils.readFileToByteArray(maleTokens));
                    outputStream.write(("\n").getBytes());
                    outputStream.write( FileUtils.readFileToByteArray(femaleTokens));

                    outputStream.flush();
                } catch (final IOException e) {
                    e.printStackTrace();

                            try {
                                Thread.sleep(5);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }


            }

        };


    }
}
