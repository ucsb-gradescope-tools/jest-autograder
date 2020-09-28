
export default class Course {
  constructor(name, units) {
    if (typeof name !== "string" || typeof units !== "number") {
      throw new Error(
        `Incorrect types passed to Course constructor; got ${typeof name} and ${typeof units}`
      );
    }

    if (units < 0) {
      throw new Error("Units can not be negative");
    }

    this.name = name;
    this.units = units;
    return this;
  }

  toString() {
    return `foo ${this.name} (${this.units} units)`;
  }

}

