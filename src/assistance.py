class AssistanceQueue:

    def __init__(self):
        self._q = []

    def queue(self):
        return self._q.copy()

    def request_assistance(self, table_no):
        if table_no in self._q:
            return False
        else:
            self._q.append(table_no)
            return True

    def resolve_assistance(self, table_no):
        if table_no in self._q:
            self._q.remove(table_no)
            return True
        else:
            return False
