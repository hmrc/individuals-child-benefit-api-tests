const getRandomElement = (arr) => {
  return arr[Math.floor(Math.random() * arr.length)];
}

const randomNino = () => {
  const number = `${Math.floor(Math.random() * 1000000)}`.padStart(6, '0');
  return `AA${number}`;
}

const randomInvalidNino = () => {
  const number = `${Math.floor(Math.random() * 100000000)}`.padStart(8, '0');
  return `ZZ${number}`;
}

const ninoPrefixGenerator = () => {
  const ValidChar1 = 'ABCEGHJKLMNOPRSTWXYZ'.split(''); // Excludes DFIQUV
  const ValidChar2 = 'ABCEGHJKLMNPRSTWXYZ'.split(''); // Excludes DFIOQUV
  const InvalidPrefixes = new Set(['BG', 'GB', 'KN', 'NK', 'NT', 'TN', 'ZZ']);

  const getValidPrefix = () => {
    let prefix;
    do {
      const c1 = ValidChar1[Math.floor(Math.random() * ValidChar1.length)];
      const c2 = ValidChar2[Math.floor(Math.random() * ValidChar2.length)];
      prefix = c1 + c2;
    } while (InvalidPrefixes.has(prefix));

    return prefix;
  };

  const prefix = getValidPrefix();
  const digits = Array.from({ length: 3 }, () => Math.floor(Math.random() * 10)).join('');

  return prefix + digits;
};


const randomGovUkOriginatorId = () => {
  const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
  const length = Math.floor(Math.random() * 38) + 3; // 3 to 40 characters long
  return Array.from({ length })
      .map(() => chars[Math.floor(Math.random() * chars.length)])
      .join('');
}

const randomUniversalCreditRecordType = () => {
  return getRandomElement(['UC', 'LCW/LCWRA']);
}

const randomUniversalCreditAction = () => {
  return getRandomElement(['Insert', 'Terminate']);
}

module.exports = {
  randomNino,
  randomInvalidNino,
  randomGovUkOriginatorId,
  randomUniversalCreditRecordType,
  randomUniversalCreditAction,
  ninoPrefixGenerator
};
