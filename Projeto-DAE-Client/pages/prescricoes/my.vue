<template>
  <div class="pt-4">
    <div>
      <b-container class="modal-content rounded-6 shadow" >
        <h1 CLASS=" p-3" style="text-align:center">Prescrições Ativas</h1>
        <b-table striped  :items="colestrol" :fields="fields" :filter="criteria" :filter-function="ativas" style="float:left;">
        <template v-slot:cell(actions)="row">
                            <nuxt-link
                              class="btn btn-dark btn-sm"
                              :to="`/biosinais/colestrol/${row.item.id}/updateColestrol`">Atualizar</nuxt-link>
                            <button
                              class="btn btn-danger btn-sm" cli
                              @click="apagar(row.item.id)">Apagar</button>
                          </template>
                        </b-table>
                        <b-row >
                          <b-col lg="6" class="pb-4"><nuxt-link   class="btn btn-dark btn-sm" to="/biosinais/colestrol/createColestrol"  >Inserir Registo</nuxt-link></b-col>
                        </b-row>
      </b-container>
    </div>

    <div class="pt-4">
      <b-container class="modal-content rounded-6 shadow" >
        <h1 CLASS=" p-3" style="text-align:center">Histórico</h1>
        <b-table striped  :items="colestrol" :filter="criteria" :filter-function="historico" :fields="fields" style="float:left;">
        </b-table>

      </b-container>
    </div>
  </div>

</template>

<script>

export default {
  data () {
    return {
      graphData: [],
      ready: false,
      labels: [],
      data: [],
      fields: [ 'dataInicio','dataFim','doutorName','tipo','descricao','actions'],
      colestrol: [],
      user: null,
      criteria: "hey",


    }
  },
  created () {

    this.$axios.$get('/api/user/'+this.$auth.user.sub+'/registers')
      .then((user) => {
        this.user = user
        this.$axios.$get('/api/prescricoes/'+this.user.id)
          .then((colestrol) => {
            this.colestrol =
              colestrol

          })

      })
  },

  methods: {

    historico: function (row){

      var hoje = (row.dataFim).split("/",3)

      var date = new Date();
      if (new Date(hoje[2].split(" ")[0],hoje[1],hoje[0])<date) {
        return true;
      }

      return false;
    },

    ativas: function (row){

      var hoje = (row.dataFim).split("/",3)

      var date = new Date();
      if (new Date(hoje[2].split(" ")[0],hoje[1],hoje[0])<date) {
        return false;
      }

      return true;
    },

  }
}
</script>
