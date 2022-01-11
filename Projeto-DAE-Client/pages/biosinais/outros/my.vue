<template>
  <div class="pt-2">
    <div style="width: 100%; display: table;">
      <div v-for="outro in outrosCat" style="display: table-row">
        <div class="p-3" style="width: 600px; display: table-cell; ">
          <b-container class="modal-content rounded-4 shadow p-4" fluid="sm">
            <h1 class="pb-4" style="text-align:center">{{outro.name}}</h1>
            <b-table striped :items="qualquer(outro.name)" :fields="fields" style="float:left;">
              <template v-slot:cell(actions)="row">
                <nuxt-link
                  class="btn btn-dark btn-sm"
                  :to="`/biosinais/outros/${row.item.id}/update`">Atualizar
                </nuxt-link>
                <button v-if="isUser"
                  class="btn btn-danger btn-sm"
                  @click="apagar(row.item.id)">Apagar
                </button>
              </template>

            </b-table>
            <b-row>
              <b-col lg="6" class="pb-4">
                <nuxt-link class="btn btn-dark btn-sm" :to="`/biosinais/outros/${outro.id}/create/`">Inserir Registo
                </nuxt-link>
              </b-col>
            </b-row>
          </b-container>
        </div>


      </div>
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
      fields: [
        {
          key: 'date',
          label: 'Data',
        },
        {
          key: 'value',
          label: 'Valor medido',
        },
        {
          key: 'descricao',
          label: 'Descrição',
        },
        {
          key: 'actions',
          label: 'Actions',

        }
      ],
      pesagem: [],
      user: null,
      outrosCat: null,

    }
  },
  created () {
    this.$axios.$get('/api/admin/outro')
      .then((outrosCat) => {
        this.outrosCat = outrosCat
      })



    this.$axios.$get('/api/user/'+this.$auth.user.sub+'/registers')
      .then((user) => {
        this.user = user
        this.$axios.$get('/api/biosinais/outro/'+this.user.id)
          .then((pesagem) => {
            this.pesagem = pesagem
          })
      })
  },

  methods: {
    apagar: function (value){

      this.$axios.$delete('/api/biosinais/outro/'+value)
        .then(() => {
          const indice = this.pesagem.findIndex(pesagem => pesagem.id === value)
          if (~indice)
            this.pesagem.splice(indice, 1)
        })
  },
    qualquer(value){
      return this.pesagem.filter(function (el){

        return el.name ==value;
      });
    }
  },
  computed: {
    // a computed getter
    isUser() {
      if (this.$auth.user.groups =="UtilizadorNormal"){
        return false;
      }
      else {
        return true;
      }
    }
  }
}
</script>
