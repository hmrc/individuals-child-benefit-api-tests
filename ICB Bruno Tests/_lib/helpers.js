
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


module.exports = {  
ninoPrefixGenerator
};
