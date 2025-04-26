# 🇿🇦 South African ID Number Validator

This Java project validates South African ID numbers according to official rules.  
It checks for format correctness, valid date of birth, gender ranges, citizenship, racial classification, and checksum integrity.

---

##  Features

- Validate ID length and ensure it consists only of digits.
- Verify **date of birth** using strict date parsing.
- Check **gender digits** fall in the expected range.
- Validate **citizenship** indicator (0 = SA citizen, 1 = Permanent Resident).
- Check **racial classification** digit is `8` (modern IDs always use 8).
- Verify **Luhn checksum** according to SA ID rules.
- Created **unit tests** for each validation rule.


---

##  How It Works

The validation process involves several stages:

### 1. Check Null or Incorrect Length
- ID must not be `null`.
- ID must be exactly **13 digits**.

### 2. Check Only Digits
- Every character must be a number (0-9).
- No letters, special symbols, or spaces allowed.

### 3. Validate Date (YYMMDD Format)
- First 6 digits represent birthdate.
- Strict date parsing used (`ResolverStyle.STRICT`) to reject impossible dates like Feb 30th.

### 4. Validate Gender Block (Digits 7–10)
- Any number from `0000` to `9999` is accepted (future enhancement: refine based on male/female codes).

### 5. Validate Citizenship Indicator (11th Digit)
- Must be either:
    - `0` → South African citizen
    - `1` → Permanent resident
- Any other value is rejected.

### 6. Validate Racial Classification (12th Digit)
- Must be exactly `8` for modern South African IDs.
- Older IDs used 0–7, but currently only 8 is valid.

### 7. Validate Using Luhn Algorithm
- Final digit (13th) is a checksum digit.
- The checksum is verified using a **modified Luhn algorithm**.

---

## 🧮 Luhn Algorithm Details

1. **Odd Positions** (1st, 3rd, 5th, etc.): sum these digits directly.
2. **Even Positions** (2nd, 4th, 6th, etc.):
    - Concatenate them.
    - Multiply the entire number by 2.
    - Sum the digits of the result.
3. Add both sums together.
4. Calculate checksum:
    - If `(total sum) % 10 == 0`, checksum is `0`.
    - Else, checksum = `10 - (total sum % 10)`.
5. Compare calculated checksum with the last digit of the ID.

---

## ⚡ Design Decisions

- **Manual digit checking** (`isOnlyDigit`) instead of regex to avoid unnecessary regex overhead.
- **Strict date validation** using Java's `LocalDate` and `ResolverStyle.STRICT`.
- **Luhn Algorithm** manually implemented instead of relying on libraries for better control and understanding.
- **Helper methods** (`validGender`, `isDateValid`, `luhnAlgorithm`) modularized for clarity and easier testing.

---

## Future Improvements

- Add better **gender detection** (e.g., 0000–4999 = Female, 5000–9999 = Male).
- Support **old ID numbers** if needed (optional racial indicators 0–7).
- Build a **REST API** to validate IDs remotely.

---

## 📜 License

This project is open-source under the **MIT License**.

---

# 🇿🇦 Built by Kganya Maleka


